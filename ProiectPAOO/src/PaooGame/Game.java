package PaooGame;

import PaooGame.Entity.Entity;
import PaooGame.Entity.Player;
import PaooGame.GameWindow.*;
import PaooGame.Graphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import static PaooGame.Graphics.MapBuilder.*;


/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable,KeyListener, MouseListener
{
    private static GameWindow       wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private static Camera camera;
    private static Player player;
    private static Entity enemy1;
    private static Entity e2;
    private static Entity friendly;
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private static boolean drawMsg;
    public Save save;
    private MainMenu menu;
    private static boolean isCreatingChar=false;

    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private static Graphics        g;          /*!< Referinta catre un context grafic.*/



    public enum GAME_STATE{
        MENU, LEVEL_SELECTION, LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, CHAR_CREATION
    }
    public static GAME_STATE state=GAME_STATE.MENU;
    public static GAME_STATE currentLevel=GAME_STATE.MENU;

    //private Tile[] tileArray;

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height)
    {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame() {
        wnd = new GameWindow("Knightly Order", GameWindow.getWidth(), GameWindow.getHeight());
        /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
        /// Se incarca toate elementele grafice (dale)
        Assets.Init();

        player = new Player();
        menu = new MainMenu(this,g);
        wnd.GetCanvas().addKeyListener(this);
        if(menu!=null) {
            System.out.printf("Added mouse listener for menu");
            wnd.GetCanvas().addMouseListener(menu);
            wnd.GetCanvas().addMouseMotionListener(menu);
        }
        wnd.GetCanvas().addMouseListener(this);

        MainMenu.initialize();
        save= new Save();
        save.database();

        int worldWidth = mapWidth * 65;
        int worldHeight = mapHeight * 67;
        camera = new Camera(GameWindow.getWidth(), GameWindow.getHeight(), worldWidth, worldHeight);
    }
    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()
    {
            /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

            /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState == true)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                if(state==GAME_STATE.LEVEL_ONE || state==GAME_STATE.LEVEL_TWO || state==GAME_STATE.LEVEL_THREE ) {
                    /// Actualizeaza pozitiile elementelor
                    Update();
                    /// Deseneaza elementele grafica in fereastra.
                    oldTime = curentTime;
                }

                Draw();
            }
            //Pentru cand jucatorul pierde
            if (player.getHealth() <= 0) {
               // wnd.showLossMessage(g);
                bs.show();
                g.dispose();


                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < 4000) {
                    // Updatam bufferul pt ca mesajul sa ramana vizibil
                    bs = wnd.GetCanvas().getBufferStrategy();
                    g = bs.getDrawGraphics();
                   // wnd.showLossMessage(g);
                    bs.show();
                    g.dispose();
                }

                // Resetam viata pentru a nu ne bloca in mesajul de loss
                player.setHealth(100);
                Game.state = Game.GAME_STATE.MENU;

                System.out.println("Returning to main menu...");
                //break; // Break out of the loop to simulate transition
            }

            if(enemy1 !=null&& enemy1.getHealth()<=0){
                //wnd.showWinningMessage(g);
                bs.show();
                g.dispose();


                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < 4000) {
                    // Updatam bufferul pt ca mesajul sa ramana vizibil
                    bs = wnd.GetCanvas().getBufferStrategy();
                    g = bs.getDrawGraphics();
                  //  wnd.showWinningMessage(g);
                    bs.show();
                    g.dispose();
                }

                // Resetam viata pentru a nu ne bloca in mesajul de loss
                enemy1 =null;
                Game.state = Game.currentLevel;

                System.out.println("Returning to the level...");
                //break; // Break out of the loop to simulate transition
            }
            if(e2!=null&&e2.getHealth()<=0){
               // wnd.showWinningMessage(g);
                bs.show();
                g.dispose();


                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < 4000) {
                    // Updatam bufferul pt ca mesajul sa ramana vizibil
                    bs = wnd.GetCanvas().getBufferStrategy();
                    g = bs.getDrawGraphics();
                   // wnd.showWinningMessage(g);
                    bs.show();
                    g.dispose();
                }

                // Resetam viata pentru a nu ne bloca in mesajul de loss
                e2=null;
                Game.state = Game.currentLevel;

                System.out.println("Returning to the level...");
                //break; // Break out of the loop to simulate transition
            }

        }

    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()
    {
        if(runState == false)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;
                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame()
    {
        if(runState == true)
        {
                /// Actualizare stare thread
            runState = false;
                /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try
            {
                    /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                    /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
              //  wnd.closeWindow();
            }
            catch(InterruptedException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else
        {
                /// Thread-ul este oprit deja.
            return;
        }
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update()
    {
        player.setEnemy(enemy1);
        player.update();
        if(enemy1 !=null)
            enemy1.update();
        if(e2!=null)
            e2.update();
        if(friendly!=null){
            friendly.update();
        }
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
        /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if (bs == null) {
            /// Se executa doar la primul apel al metodei Draw()
            try {
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            } catch (Exception e) {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
        /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
        /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        g.setColor(Color.black);
        g.fillRect(0,0,wnd.GetWndWidth(), wnd.GetWndHeight());

        /// operatie de desenare
        // ...............
        //Tile.WoodBox.Draw(g,2*Tile.TILE_WIDTH,0);
        //g.translate((int)-camera.getX(), (int)-camera.getY());
        if(state==GAME_STATE.LEVEL_ONE || state==GAME_STATE.LEVEL_TWO || state==GAME_STATE.LEVEL_THREE) {
            //TODO fix attack registering
            camera.centerOnEntity(player);

            // Apply camera translation
            g.translate((int) -camera.getX(), (int) -camera.getY());
            MapBuilder.mapBuilder();
            MapBuilder.draw(g);
            player.render(g);
            if(enemy1 !=null)
                enemy1.render(g);
            if(  state==GAME_STATE.LEVEL_TWO  ) {
                if (e2 != null)
                    e2.render(g);

            }
            if(  state==GAME_STATE.LEVEL_THREE ) {
                if (friendly != null) {
                    friendly.render(g);
                }
            }
            camera.centerOnEntity(player);
            //g.translate((int) camera.getX(), (int) camera.getY());
        }else if (state == GAME_STATE.MENU || state == GAME_STATE.LEVEL_SELECTION) {
            g.drawImage(Assets.background, 0, 0, null);
            camera.backToZero();
            MainMenu.render(g);
        }else if (state==GAME_STATE.CHAR_CREATION){
            CharacterCreation creation = new CharacterCreation(new DynamicAssetBuilder(64,256));
            if(!isCreatingChar) {
                wnd.GetCanvas().addMouseListener(creation);
                isCreatingChar = true;
                creation.render();
            }

        }
        if(drawMsg){
            if (g != null) {
                // Replace with actual message drawing code
                Font fnt0=new Font("Arial",Font.PLAIN,25);
                g.setFont(fnt0);
                g.setColor(Color.BLACK);
                g.drawString("Ai un milion pana luni?", (int)friendly.getX(), (int)friendly.getY());
            } else {
                System.out.println("DEBUG: Graphics context is null");
            }
            new Timer(40000, e -> {
                g.clearRect((int) friendly.getX(), (int) friendly.getY() - 100, 500, 100); // Adjust the dimensions as needed
                ((Timer) e.getSource()).stop();
            }).start();
            drawMsg=false;
        }
        //     g.drawRect(1 * Tile.TILE_WIDTH, 1 * Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);


        // end operatie de desenare
        /// Se afiseaza pe ecran
        bs.show();

        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }



    //implementarea metodelor pt keylistener
    @Override
        public void keyPressed(KeyEvent e)
        {
            int keyCode = e.getKeyCode();
            if(!player.CollisionOn && (state==GAME_STATE.LEVEL_ONE || state==GAME_STATE.LEVEL_TWO || state==GAME_STATE.LEVEL_THREE)) {
                if (keyCode == KeyEvent.VK_A) {
                    // tasta A misca jucatorul la stanga
                    player.moveLeft(g);
                   // System.out.println("Misc Stanga"); //debug
                } else if (keyCode == KeyEvent.VK_D) {
                    // D misca la dreapta
                    player.moveRight(g);
                    //System.out.println("Misc Dreapta"); //debug
                } else if (keyCode == KeyEvent.VK_W) {
                    // W jucatorul sare
                    player.moveUp(g);
                    //System.out.println("Misc Sus");
                } else if (keyCode == KeyEvent.VK_S) {
                    player.moveDown(g);
                    //System.out.println("Misc Jos");
                }  else if (keyCode == KeyEvent.VK_L) {
                    int level=0;
                    if(state==GAME_STATE.LEVEL_ONE){
                        level=2;
                    }else if(state==GAME_STATE.LEVEL_TWO){
                        level=3;
                    }else if(state==GAME_STATE.LEVEL_THREE){
                    }
                    Save.insertData(level,0,0,0,0);
                    //System.out.print("Score saved");
                }
            }else if(keyCode == KeyEvent.VK_R){
                System.out.println("Progress resetted.");
                Save.insertData(0,0,0,0,0);
            }else if(keyCode == KeyEvent.VK_K){
                state=GAME_STATE.CHAR_CREATION;
                System.out.println("We tried entering CHAR_CREATION?");
            }
            else{
                player.CollisionOn=false;
                player.stopRunning();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S)
        {
            player.setCurrentFrame(0);
            player.stopRunning();
        }

    }

        @Override
        public void keyTyped(KeyEvent e) {
        // implementare daca o tasta a fost tastata ( apasata + eliberata )
        // nu e nevoie momentan
    }

    public static Entity getEntity(){
        return enemy1;
    }
    public static Entity getEntity2(){
        return e2;
    }
    public static Entity getFriendly(){
        return friendly;
    }
    public static void setEntity(Entity.EntityType Type, int Health){
        enemy1 =new Entity(Type,Health,true);
    }
    public static void setEntity2(Entity.EntityType Type, int Health){
        e2=new Entity(Type,Health,true);
    }
    public static void setFriendly(int Health){
        friendly=new Entity(Entity.EntityType.FRIENDLY, Health,false);
    }
    public static void setPlayerCoords(int newX,int newY){
        player.setX(newX);
        player.setY(newY);
    }

    public static GameWindow getGameWnd(){
        return wnd;
    }

    public static Camera retCamera(){
        return camera;
    }
    public static Graphics getGraphicalContext(){
        return g;
    }
    public static void drawMessage(double x, double y) {
        drawMsg=true;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
//        if(state != GAME_STATE.MENU && state!=GAME_STATE.LEVEL_SELECTION) {
//            if(!Player.getAttackingState()) {
//                Player.setAttackingState(true);
//
//
//                if(Player.getEnemyCollision()){
//
//                    enemy1.setHealth(enemy1.getHealth()-10);
//                    System.out.println("New health" + enemy1.getHealth());
//                }
//            }
//
//
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

