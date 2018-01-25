package testing;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.junit.Before;
import org.junit.Test;

import application.MapLoader;

public class TestMapLoader
{
	
	@Before
	public void setUp() throws Exception
	{
		
	}

	@Test
	public void testMapDataLoadedCorrectly()
	{
		MapLoader.getInstance().loadMap("4\n4\n3 2 20 1\n20 21 20 2\n2 21 22 20\n3 20 2 20");
		assertEquals(20, MapLoader.getInstance().getMapValue(0, 1));
		assertEquals(20, MapLoader.getInstance().getMapValue(2, 1));
		assertEquals(2, MapLoader.getInstance().getMapValue(2, 3));
	}
	
	@Test(expected = NoSuchFileException.class)
	public void testNonExistantFile() throws IOException
	{
		File test = new File("C:/afilewhichdoesnotexist.txt");
		MapLoader.getInstance().loadMap(test);
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testOutOfBoundsException()
	{
		MapLoader.getInstance().loadMap("4\n4\n3 2 20 1\n20 21 20 2\n2 21 22 20\n3 20 2 20");
		MapLoader.getInstance().getMapValue(4, 4);
	}
	
	@Test
	public void testNumberofRowsAndCols()
	{
		MapLoader.getInstance().loadMap("4\n4\n3 2 20 1\n20 21 20 2\n2 21 22 20\n3 20 2 20");
		assertEquals(4, MapLoader.getInstance().getRows());
		assertEquals(4, MapLoader.getInstance().getCols());
	}

}
