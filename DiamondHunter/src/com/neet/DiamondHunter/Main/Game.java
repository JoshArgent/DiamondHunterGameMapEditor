// The entry point of the game.
// This class loads up a JFrame window and
// puts a GamePanel into it.

package com.neet.DiamondHunter.Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

import com.neet.DiamondHunter.Manager.JukeBox;

public class Game {
	
	public static File mapFile;
	
	public static void main(String[] args) {
		
		if(args.length > 0)
		{
			File file = new File(args[0]);
			if(file.exists())
			{
				mapFile = file;
			}
		}
		
		JFrame window = new JFrame("Diamond Hunter");
		
		window.add(new GamePanel());
		
		window.setResizable(false);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		if(mapFile != null)
			window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		else
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.addWindowListener(new WindowAdapter()
		{
			@Override
            public void windowClosing(WindowEvent event)
            {
				JukeBox.stopAll();
            }
        });
		
	}
	
}
