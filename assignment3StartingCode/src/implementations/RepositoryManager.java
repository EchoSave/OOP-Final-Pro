package implementations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Handles saving and loading the WordTracker binary search tree.
 * The tree is stored in a binary file named repository.ser.
 *
 * @author Irteza Hassan
 */
public class RepositoryManager
{
	private static final String REPOSITORY_FILE = "repository.ser";

	/**
	 * Loads the saved binary search tree from repository.ser.
	 * If the file does not exist, a new empty tree is returned.
	 *
	 * @return the restored tree, or a new empty tree if no file exists
	 */
	@SuppressWarnings("unchecked")
	public static BSTree<Word> load()
	{
		File file = new File(REPOSITORY_FILE);

		if(!file.exists())
		{
			System.out.println(
					"No repository found. Starting with a new tree.");

			return new BSTree<Word>();
		}

		try(
				FileInputStream fileInput =
						new FileInputStream(file);

				ObjectInputStream objectInput =
						new ObjectInputStream(fileInput)
		)
		{
			BSTree<Word> tree =
					(BSTree<Word>) objectInput.readObject();

			System.out.println(
					"Repository loaded successfully.");

			return tree;
		}
		catch(IOException | ClassNotFoundException exception)
		{
			System.out.println(
					"Could not load repository: "
					+ exception.getMessage());

			System.out.println(
					"Starting with a new empty tree.");

			return new BSTree<Word>();
		}
	}

	/**
	 * Saves the current binary search tree to repository.ser.
	 *
	 * @param tree the tree to save
	 * @throws NullPointerException if the tree is null
	 */
	public static void save(BSTree<Word> tree)
	{
		if(tree == null)
		{
			throw new NullPointerException(
					"Tree cannot be null.");
		}

		try(
				FileOutputStream fileOutput =
						new FileOutputStream(
								REPOSITORY_FILE);

				ObjectOutputStream objectOutput =
						new ObjectOutputStream(fileOutput)
		)
		{
			objectOutput.writeObject(tree);

			System.out.println(
					"Repository saved successfully.");
		}
		catch(IOException exception)
		{
			System.out.println(
					"Could not save repository: "
					+ exception.getMessage());
		}
	}
}