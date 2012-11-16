package minijava.dexstruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import minijava.dexstruct.items.StringItem;

/**
 * StringTable�ε����ݴ洢�ṹ
 */
public class DexStringTable {
	private int size;
	
	private HashSet<String> strings;
	private ArrayList<String> aStrings;
	
	private ArrayList<StringItem> stringItemsArrayList;
	
	/**
	 * ���캯��
	 */
	public DexStringTable()
	{
		strings = new HashSet<String>();
	}
	
	/**
	 * ���һ���ַ���
	 * @param _str ��ӵ��ַ���
	 */
	public void AddString(String _str)
	{
		strings.add(_str);
	}
	
	/**
	 * ���򣬲���Ҫ�κ�׼��������
	 */
	public void Serilize()
	{
		stringItemsArrayList = new ArrayList<StringItem>();
		
		aStrings = new ArrayList<String>();
		Iterator<String> it = strings.iterator();
		for (int i = 0; i < strings.size(); i++) 
		{
			aStrings.add(new String(it.next()));
		}
		
		for (int i = 0; i < aStrings.size()-1; i++) 
		{
			for (int j = i; j < aStrings.size(); j++) 
			{
				if (aStrings.get(i).compareTo(aStrings.get(j)) > 0) 
				{
					String tmpString = aStrings.get(i);
					aStrings.set(i, aStrings.get(j));
					aStrings.set(j, tmpString);
				}
			}
		}
		
		//sort by ABC
		for (int i = 0; i < aStrings.size(); i++) 
		{
			stringItemsArrayList.add(new StringItem(aStrings.get(i)));		
		}
		size = aStrings.size();
	}
	
	/**
	 * ͨ���ַ�����ȡ��Ӧ��index��StringTable�У�
	 * @param str �ַ���
	 * @return ��Ӧ��index��StringTable�У�
	 */ 
	public int GetStringIdx(String str)
	{
		if (strings.contains(str)) 
			return aStrings.indexOf(str);
		return -1;
	}
	
	/**
	 * ͨ���ַ�����index��ȡ��Ӧ���ַ���
	 * @param idx �ַ�����index
	 * @return ��Ӧ���ַ���
	 */
	public String GetString(int idx)
	{
		if (idx < size)
			return aStrings.get(idx);
		return null;
	}
	
	/**
	 * ��ȡ��Ŀ����
	 * @return ��Ŀ����
	 */
	public int GetSize()
	{
		return size;
	}
	
	/**
	 * ����ĳһ��Ŀ�Ļ���offset
	 * @param idx ��Ŀindex
	 * @param off ����offset
	 */
	public void SetBackPatchOff(int idx, int off)
	{
		stringItemsArrayList.get(idx).backPatchOff = off;
	}
	
	/**
	 * ��ȡĳһ��Ŀ�Ļ���offset
	 * @param idx ��Ŀindex
	 * @return ����offset
	 */
	public int GetBackPatchOff(int idx)
	{
		return stringItemsArrayList.get(idx).backPatchOff;
	}
}