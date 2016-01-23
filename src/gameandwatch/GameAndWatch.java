/*
 * The MIT License
 *
 * Copyright 2016 Romain Porte (MicroJoe) <microjoe at mailoo.org>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gameandwatch;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Grosse classe contenant toutes les informations concernant un jeu
 * @author Romain Porte (MicroJoe) <microjoe at mailoo.org>
 */
public class GameAndWatch implements Serializable {

    // Constantes
    private static final int BUFFER_SIZE = 2048;
    private static final String ZIP_XML_FILE = "game.xml";
    private static final String ZIP_IMAGE_DIR = "images/"; // Ne pas oublier le slash à la fin du nom de dossier

    // Attributs
    private Player player;
    private ArrayList<Item> items;
    private Generateur generateur;

    // Constructeurs

    public GameAndWatch() {
    }

    public GameAndWatch(Player player, ArrayList<Item> items, Generateur generateur) {
        this.player = player;
        this.items = items;
        this.generateur = generateur;
    }
    
    // Getters & setters

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Generateur getGenerateur() {
        return generateur;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setGenerateur(Generateur generateur) {
        this.generateur = generateur;
    }
    
    // Méthodes utiles

    /**
     * Ecrit l'ensemble du jeu dans un fichier ZIP
     * @param nom Le nom du fichier zip à créer (exemple: "toto.zip")
     * @param spriteDir Le chemin vers le dossier contenant toutes les images
     * @throws IOException 
     */
    public void toZip(String nom, String spriteDir) throws IOException {
        try {
            // Ecriture du fichier XML en mémoire
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            toXml(output);
            byte[] xmlBytes = output.toByteArray();

            // Création du flux de sortie
            BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(nom));
            
            try (ZipOutputStream out = new ZipOutputStream(buff)) {

                // Configuration du fichier ZIP
                out.setMethod(ZipOutputStream.DEFLATED);
                out.setLevel(9);

                // Ecriture du fichier XML
                BufferedInputStream bi = new BufferedInputStream(new ByteArrayInputStream(xmlBytes), BUFFER_SIZE);
                ZipEntry xmlEntry = new ZipEntry("game.xml");
                out.putNextEntry(xmlEntry);

                byte data[] = new byte[BUFFER_SIZE];
                int xmlCount;
                while ((xmlCount = bi.read(data, 0, BUFFER_SIZE)) != -1) {
                    out.write(data, 0, xmlCount);
                }
                out.closeEntry();

                // Ecriture des images depuis un dossier
                if (spriteDir != null) {
                    File directory = new File(spriteDir);
                    File[] files = directory.listFiles();

                    for (File file : files) {
                        try (BufferedInputStream buffi = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE)) {

                            // Création de l'entrée
                            ZipEntry entry = new ZipEntry(ZIP_IMAGE_DIR + file.getName());
                            out.putNextEntry(entry);

                            // Ecriture du fichier
                            int count;
                            while ((count = buffi.read(data, 0, BUFFER_SIZE)) != -1) {
                                out.write(data, 0, count);
                            }

                            // Fermeture de l'entrée
                            out.closeEntry();
                        }
                    } // Fin de la boucle d'ecriture d'images
                    }
            } // Fermeture du fichier zip
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameAndWatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ecrit l'objet sous forme XML dans un flux
     * @param stream Le flux dans lequel ecrire le document XML
     */
    public void toXml(OutputStream stream) {
        try (XMLEncoder enc = new XMLEncoder(stream)) {
            enc.writeObject(this);
            enc.flush();
        }
    }
    
    /**
     * Charge un jeu depuis un fichier XML
     * @param stream Le flux à partir duquel charger le fichier XML
     * @return Le jeu chargé (sans images)
     */
    public static GameAndWatch fromXml(InputStream stream) {
        GameAndWatch game;
        
        try (XMLDecoder dec = new XMLDecoder(stream)) {
            game = (GameAndWatch) dec.readObject();
        }
        
        return game;
    }

    /**
     * Charge un jeu depuis un fichier ZIP
     * @param filepath Chemin vers le fichier zip
     * @param imgStore Store d'images qui contenir les images chargées
     * @return Le jeu chargé
     * @throws IOException 
     */
    public static GameAndWatch fromZip(String filepath, ImageStoreInterface imgStore) throws IOException {
        GameAndWatch game = null;
        
        // Ouverture du fichier zip
        try (ZipFile zf = new ZipFile(filepath)) {
            Enumeration<? extends ZipEntry> entries = zf.entries();

            // Pour chaque entrée dans le fichier zip
            while (entries.hasMoreElements()) {
                ZipEntry e = (ZipEntry) entries.nextElement();

                // Si c'est un le fichier XML on charge le jeu depuis le fichier
                if (e.getName().equals(ZIP_XML_FILE)) {
                    game = fromXml(zf.getInputStream(e));
                }
                
                // Sinon si c'est une image on la charge
                else if (e.getName().startsWith("images/")) {
                    imgStore.addImage(e.getName().replaceAll("images/", ""), zf.getInputStream(e));
                }
            }
        }
        
        return game;
    }

    @Override
    public String toString() {
        return "GameAndWatch{" + "player=" + player + ", items=" + items + ", generateur=" + generateur + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.player);
        hash = 89 * hash + Objects.hashCode(this.items);
        hash = 89 * hash + Objects.hashCode(this.generateur);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameAndWatch other = (GameAndWatch) obj;
        if (!Objects.equals(this.player, other.player)) {
            return false;
        }
        if (!Objects.equals(this.items, other.items)) {
            return false;
        }
        if (!Objects.equals(this.generateur, other.generateur)) {
            return false;
        }
        return true;
    }
    
    
}
