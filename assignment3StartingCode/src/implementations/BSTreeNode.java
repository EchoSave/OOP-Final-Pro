package implementations;

import java.io.Serializable;

/**
 * Represents one node in the binary search tree.
 *
 * @param <E> the type of element stored in the node
 */
public class BSTreeNode<E extends Comparable<? super E>>
		implements Serializable
{
	private static final long serialVersionUID = 1L;

	private E element;
	private BSTreeNode<E> left;
	private BSTreeNode<E> right;

	/**
	 * Creates a new tree node with the given element.
	 *
	 * @param element the element stored in this node
	 * @throws NullPointerException if the element is null
	 */
	public BSTreeNode(E element)
	{
		if(element == null)
		{
			throw new NullPointerException("Element cannot be null.");
		}

		this.element = element;
		this.left = null;
		this.right = null;
	}

	/**
	 * Returns the element stored in this node.
	 *
	 * @return the stored element
	 */
	public E getElement()
	{
		return element;
	}

	/**
	 * Replaces the element stored in this node.
	 *
	 * @param element the new element
	 * @throws NullPointerException if the element is null
	 */
	public void setElement(E element)
	{
		if(element == null)
		{
			throw new NullPointerException("Element cannot be null.");
		}

		this.element = element;
	}

	/**
	 * Returns the left child node.
	 *
	 * @return the left child, or null if none exists
	 */
	public BSTreeNode<E> getLeft()
	{
		return left;
	}

	/**
	 * Sets the left child node.
	 *
	 * @param left the new left child
	 */
	public void setLeft(BSTreeNode<E> left)
	{
		this.left = left;
	}

	/**
	 * Returns the right child node.
	 *
	 * @return the right child, or null if none exists
	 */
	public BSTreeNode<E> getRight()
	{
		return right;
	}

	/**
	 * Sets the right child node.
	 *
	 * @param right the new right child
	 */
	public void setRight(BSTreeNode<E> right)
	{
		this.right = right;
	}
}