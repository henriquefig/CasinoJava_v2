package roulete;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.*;
public class Sound{

   Clip clip;
   BooleanControl muteControl;

   public Sound() {
   }

   public Sound(String a) {

      try {
         // Open an audio input stream.
         URL url = this.getClass().getClassLoader().getResource(a);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         // Open audio clip and load samples from the audio input stream.

         clip = AudioSystem.getClip();
         clip.open(audioIn);
         clip.start();
        
         muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
    
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public void Close()
   {
      clip.close();
   }
   public void Stopsound()
   {
      clip.stop();
   }
   public void ToggleMute()
   {
      muteControl.setValue(!muteControl.getValue());
   }
   public void Startsound()
   {
      clip.start();
   }
}