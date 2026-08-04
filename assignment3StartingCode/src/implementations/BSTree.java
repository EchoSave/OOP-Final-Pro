package implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import utilities.BSTreeADT;
import utilities.Iterator;

/**
 * Binary Search Tree implementation.
 *
 * @param <E> the type of elements stored in the tree
 */
public class BSTree<E extends Comparable<? super E>>
		implements BSTreeADT<E>, Serializable
{
	private static final long serialVersionUID = 1L;

	private BSTreeNode<E> root;
	private int size;

	/**
	 * Creates an empty binary search tree.
	 */
	public BSTree()
	{
		root = null;
		size = 0;
	}

	/**
	 * Creates a binary search tree with an initial root element.
	 *
	 * @param element the first element stored in the tree
	 * @throws NullPointerException if the element is null
	 */
	public BSTree(E element)
	{
		if(element == null)
		{
			throw new NullPointerException("Element cannot be null.");
		}

		root = new BSTreeNode<E>(element);
		size = 1;
	}

	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException
	{
		if(root == null)
		{
			throw new NullPointerException("The tree is empty.");
		}

		return root;
	}

	@Override
	public int getHeight()
	{
		return height(root);
	}

	/**
	 * Recursively calculates the height of a subtree.
	 *
	 * @param node the current node
	 * @return the height of the subtree
	 */
	private int height(BSTreeNode<E> node)
	{
		if(node == null)
		{
			return 0;
		}

		return 1 + Math.max(
				height(node.getLeft()),
				height(node.getRight()));
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public void clear()
	{
		root = null;
		size = 0;
	}

	@Override
	public boolean contains(E entry) throws NullPointerException
	{
		if(entry == null)
		{
			throw new NullPointerException("Entry cannot be null.");
		}

		return search(entry) != null;
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException
	{
		if(entry == null)
		{
			throw new NullPointerException("Entry cannot be null.");
		}

		return searchNode(root, entry);
	}

	/**
	 * Recursively searches for an entry.
	 *
	 * @param node the current node
	 * @param entry the entry to search for
	 * @return the matching node, or null if not found
	 */
	private BSTreeNode<E> searchNode(BSTreeNode<E> node, E entry)
	{
		if(node == null)
		{
			return null;
		}

		int comparison = entry.compareTo(node.getElement());

		if(comparison == 0)
		{
			return node;
		}
		else if(comparison < 0)
		{
			return searchNode(node.getLeft(), entry);
		}
		else
		{
			return searchNode(node.getRight(), entry);
		}
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException
	{
		if(newEntry == null)
		{
			throw new NullPointerException("New entry cannot be null.");
		}

		if(root == null)
		{
			root = new BSTreeNode<E>(newEntry);
			size = 1;
			return true;
		}

		BSTreeNode<E> current = root;

		while(true)
		{
			int comparison = newEntry.compareTo(current.getElement());

			if(comparison == 0)
			{
				return false;
			}
			else if(comparison < 0)
			{
				if(current.getLeft() == null)
				{
					current.setLeft(new BSTreeNode<E>(newEntry));
					size++;
					return true;
				}

				current = current.getLeft();
			}
			else
			{
				if(current.getRight() == null)
				{
					current.setRight(new BSTreeNode<E>(newEntry));
					size++;
					return true;
				}

				current = current.getRight();
			}
		}
	}

	@Override
	public BSTreeNode<E> removeMin()
	{
		if(root == null)
		{
			return null;
		}

		if(root.getLeft() == null)
		{
			BSTreeNode<E> minimum = root;
			root = root.getRight();
			size--;
			return minimum;
		}

		BSTreeNode<E> parent = root;
		BSTreeNode<E> current = root.getLeft();

		while(current.getLeft() != null)
		{
			parent = current;
			current = current.getLeft();
		}

		parent.setLeft(current.getRight());
		size--;

		return current;
	}

	@Override
	public BSTreeNode<E> removeMax()
	{
		if(root == null)
		{
			return null;
		}

		if(root.getRight() == null)
		{
			BSTreeNode<E> maximum = root;
			root = root.getLeft();
			size--;
			return maximum;
		}

		BSTreeNode<E> parent = root;
		BSTreeNode<E> current = root.getRight();

		while(current.getRight() != null)
		{
			parent = current;
			current = current.getRight();
		}

		parent.setRight(current.getLeft());
		size--;

		return current;
	}

	@Override
	public Iterator<E> inorderIterator()
	{
		ArrayList<E> list = new ArrayList<E>();
		inorder(root, list);
		return new ListIterator<E>(list);
	}

	/**
	 * Adds elements to the list in left-root-right order.
	 *
	 * @param node the current node
	 * @param list the list holding the traversal results
	 */
	private void inorder(BSTreeNode<E> node, ArrayList<E> list)
	{
		if(node == null)
		{
			return;
		}

		inorder(node.getLeft(), list);
		list.add(node.getElement());
		inorder(node.getRight(), list);
	}

	@Override
	public Iterator<E> preorderIterator()
	{
		ArrayList<E> list = new ArrayList<E>();
		preorder(root, list);
		return new ListIterator<E>(list);
	}

	/**
	 * Adds elements to the list in root-left-right order.
	 *
	 * @param node the current node
	 * @param list the list holding the traversal results
	 */
	private void preorder(BSTreeNode<E> node, ArrayList<E> list)
	{
		if(node == null)
		{
			return;
		}

		list.add(node.getElement());
		preorder(node.getLeft(), list);
		preorder(node.getRight(), list);
	}

	@Override
	public Iterator<E> postorderIterator()
	{
		ArrayList<E> list = new ArrayList<E>();
		postorder(root, list);
		return new ListIterator<E>(list);
	}

	/**
	 * Adds elements to the list in left-right-root order.
	 *
	 * @param node the current node
	 * @param list the list holding the traversal results
	 */
	private void postorder(BSTreeNode<E> node, ArrayList<E> list)
	{
		if(node == null)
		{
			return;
		}

		postorder(node.getLeft(), list);
		postorder(node.getRight(), list);
		list.add(node.getElement());
	}

	/**
	 * Iterator implementation used by the tree traversals.
	 *
	 * @param <T> the type of elements returned by the iterator
	 */
	private static class ListIterator<T> implements Iterator<T>
	{
		private ArrayList<T> list;
		private int index;

		/**
		 * Creates an iterator using the provided list.
		 *
		 * @param list the traversal result list
		 */
		public ListIterator(ArrayList<T> list)
		{
			this.list = list;
			this.index = 0;
		}

		@Override
		public boolean hasNext()
		{
			return index < list.size();
		}

		@Override
		public T next() throws NoSuchElementException
		{
			if(!hasNext())
			{
				throw new NoSuchElementException(
						"No more elements are available.");
			}

			return list.get(index++);
		}
	}
}