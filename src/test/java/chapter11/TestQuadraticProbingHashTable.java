package chapter11;

import base.structures.HashTable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static chapter11.TestHashTableBase.*;
import static junit.framework.TestCase.*;

/**
 * Тестирование сущности {@link QuadraticProbingHashTable}
 *
 * @author rassoll
 * @created 12.02.2018
 * @$Author$
 * @$Revision$
 */
public class TestQuadraticProbingHashTable
{
	private static final int REAL_HASH_TABLE_SIZE = HashTable.getPrime(HASH_TABLE_SIZE);
	private static QuadraticProbingHashTable hashTable;
	private static ArrayList<DataItem> additionalItems;

	{
		additionalItems = new ArrayList<>();
		IntStream.range(100_000, 100_010).forEach((value) -> additionalItems.add(new DataItem(value)));
	}

	@Before
	public void init()
	{
		hashTable = new QuadraticProbingHashTable(HASH_TABLE_SIZE);

		IntStream.range(0, HASH_TABLE_SIZE / 2).forEach(key -> hashTable.insert(new DataItem(key)));
	}

	@Test
	public void testHashFunction()
	{
		assertEquals(1, hashTable.hashFunction(ITEM_1));
		assertEquals(0, hashTable.hashFunction(ITEM_2));
		assertEquals(150, hashTable.hashFunction(ITEM_3));
		assertEquals(199, hashTable.hashFunction(ITEM_4));
	}

	@Test
	public void testInsertMethod()
	{
		TestHashTableBase.testInsertMethod(hashTable);
	}

	@Test
	public void testDeleteMethod()
	{
		TestHashTableBase.testDeleteMethod(hashTable);
	}

	@Test
	public void testGetDisplayDataMethod()
	{
		TestHashTableBase.testGetDisplayDataMethod(hashTable);
	}

	/**
	 * Тестирование метода получения первого следующего за переданным значением, простого числа
	 */
	@Test
	public void testGetPrimeMethod()
	{
		assertEquals(3, HashTable.getPrime(2));
		assertEquals(5, HashTable.getPrime(3));
		assertEquals(7, HashTable.getPrime(6));
	}

	/**
	 * Тестирование метода проверки того, что переданное число является простым
	 */
	@Test
	public void checkIsPrimeMethod()
	{
		assertTrue(HashTable.isPrime(11));
		assertTrue(HashTable.isPrime(333));
		assertTrue(HashTable.isPrime(555));

		assertFalse(HashTable.isPrime(10));
		assertFalse(HashTable.isPrime(300));
		assertFalse(HashTable.isPrime(55556));
	}

	@Test
	public void checkGettingElementsNumber()
	{
		TestHashTableBase.checkGettingElementsNumber(hashTable, HASH_TABLE_SIZE / 2, additionalItems);
	}

	@Test
	public void checkHashTableSize()
	{
		assertEquals(REAL_HASH_TABLE_SIZE, hashTable.getHashTableSize());
	}

	@Test
	public void testGettingLoadFactor()
	{
		float size = (REAL_HASH_TABLE_SIZE / 2) / (float) REAL_HASH_TABLE_SIZE;

		assertEquals(size, hashTable.getLoadFactor());

		additionalItems.forEach(hashTable::insert);

		size += (additionalItems.size() / (float) REAL_HASH_TABLE_SIZE);

		assertEquals(size, hashTable.getLoadFactor());
	}
}
