package minijava.dexstruct;

import java.util.*;

import minijava.dexstruct.items.ProtoItem;

/**
 * ProtoTable�ε����ݴ洢�ṹ
 */
public class DexProtoTable {
	private int size;
	private DexTypeTable typeTable;
	private DexStringTable stringTable;
	
	private HashSet<String> hModifiedProtoShortySet;
	private ArrayList<ProtoItem> aProtoItems;
	private HashMap<String, Integer> hModifiedShortyToProtoIdx;
	private HashMap<Integer, String> hProtoIdxToModifiedShorty;
	
	/**
	 * ���캯������Ҫ�������table����Ϣ
	 * @param _typeTable
	 * @param _stringTable
	 */
	public DexProtoTable(DexTypeTable _typeTable, DexStringTable _stringTable) 
	{
		typeTable = _typeTable;
		stringTable = _stringTable;
		hModifiedProtoShortySet = new HashSet<String>();
		aProtoItems = new ArrayList<ProtoItem>();
		hProtoIdxToModifiedShorty = new HashMap<Integer, String>();
		hModifiedShortyToProtoIdx = new HashMap<String, Integer>();
	}
	
	/**
	 * ���һ������ԭ��
	 * @param shorty ����ԭ�͵Ķ����������������й������������ɵĽ���
	 * @param rtype ����ԭ�͵ķ���ֵ��������
	 * @param _paramnum ����ԭ�͵Ĳ�������
	 * @param params ����ԭ�͵Ĳ����������б����ﴫ�������ַ�����������ã���ֱ��ʹ�õ����ö����Ǹ������ݣ���֮�������޸Ļ���������Ӱ��
	 */
	public void AddProto(String shorty, String rtype, int _paramnum, String[] params)
	{
		String modShorty = new String(shorty);
		modShorty = modShorty + rtype;
		for (int i = 0; i < _paramnum; i++) 
		{
			modShorty = modShorty + params[i];
		}
		if(hModifiedProtoShortySet.add(new String(modShorty)))
		{
			stringTable.AddString(shorty);
			typeTable.AddType(rtype);
			aProtoItems.add(new ProtoItem(modShorty, shorty, rtype, _paramnum, params));
			if (_paramnum != 0) 
			{
				for (int i = 0; i < _paramnum; i++) 
				{
					typeTable.AddType(params[i]);
				}
			}
		}
	}
	
	/**
	 * ����Ҫ�� StringTable �� TypeTable ������ Serilize() �������
	 */
	public void Serilize()
	{
		for (int i = 0; i < aProtoItems.size(); i++) 
		{
			aProtoItems.get(i).shortyIdx = stringTable.GetStringIdx(aProtoItems.get(i).shorty);
			aProtoItems.get(i).returnTypeIdx = typeTable.GetTypeIdx(aProtoItems.get(i).returnType);
			if (aProtoItems.get(i).paramNum != 0) 
			{
				for (int j = 0; j < aProtoItems.get(i).paramNum; j++) 
				{
					aProtoItems.get(i).typeList.typeidx[j] = (short)typeTable.GetTypeIdx(aProtoItems.get(i).paramStrings[j]); 
				}
			}
		}
		
		for (int i = 0; i < aProtoItems.size()-1; i++) 
		{
			for (int j = i+1; j < aProtoItems.size(); j++) 
			{
				if (aProtoItems.get(i).returnTypeIdx > aProtoItems.get(j).returnTypeIdx) 
				{
					ProtoItem tmpItem = aProtoItems.get(i);
					aProtoItems.set(i, aProtoItems.get(j));
					aProtoItems.set(j, tmpItem);
				}
				else if (aProtoItems.get(i).returnTypeIdx == aProtoItems.get(j).returnTypeIdx)
				{
					int parami = 0;
					while (true)
					{
						if (aProtoItems.get(i).paramNum > parami && aProtoItems.get(j).paramNum <= parami) 
						{
							//System.out.println(aProtoItems.get(i).shorty + ": j have less params:" + aProtoItems.get(j).shorty + parami);
							ProtoItem tmpItem = aProtoItems.get(i);
							aProtoItems.set(i, aProtoItems.get(j));
							aProtoItems.set(j, tmpItem);
							break;
						}
						else if (aProtoItems.get(i).paramNum <= parami && aProtoItems.get(j).paramNum > parami) 
						{
							//System.out.println(aProtoItems.get(i).shorty + ": i have less patams" + aProtoItems.get(j).shorty);
							break;
						}
						else if (aProtoItems.get(i).paramNum > parami && aProtoItems.get(j).paramNum > parami) 
						{
							int itypeidx = typeTable.GetTypeIdx(aProtoItems.get(i).paramStrings[parami]);
							int jtypeidx = typeTable.GetTypeIdx(aProtoItems.get(j).paramStrings[parami]);
							//if (typeTable.GetTypeIdx(aProtoItems.get(i).paramStrings[parami]) > typeTable.GetTypeIdx(aProtoItems.get(j).paramStrings[parami])) 
							if (itypeidx > jtypeidx)
							{
								//System.out.println(aProtoItems.get(i).paramStrings[parami] + " idx bigger than "
										//+ aProtoItems.get(j).paramStrings[parami] );
								ProtoItem tmpItem = aProtoItems.get(i);
								aProtoItems.set(i, aProtoItems.get(j));
								aProtoItems.set(j, tmpItem);
								break;
							}
							else if (itypeidx < jtypeidx)
							{
								break;
							}
						}
						else 
						{
							break;
						}
						parami++;
					}
				}
			}
		}
		
		for (int i = 0; i < aProtoItems.size(); i++) 
		{
			hModifiedShortyToProtoIdx.put(aProtoItems.get(i).modifiedShorty, i);
			hProtoIdxToModifiedShorty.put(i, aProtoItems.get(i).modifiedShorty);
		}
		
		size = aProtoItems.size();
	}
	
	/**
	 * ����һ������λ�ã�����֯dex�ṹʱ���õ�
	 * @param idx ��Ҫ����ı����index
	 * @param off ��Ҫ�����dex�ļ��е�λ��
	 */
	public void SetBackPatchOff(int idx, int off)
	{
		if (aProtoItems.get(idx).paramNum != 0) 
		{
			aProtoItems.get(idx).parametersOffBackPatchOffset = off;
		}
		else 
		{
			aProtoItems.get(idx).parametersOffBackPatchOffset = 0;
		}
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
	 * ͨ��ԭ�͵���Ϣ��ȡԭ�͵�index
	 * @param shorty ����ԭ�͵�shorty�����ַ���
	 * @param rtype ����ԭ�͵ķ���ֵ������
	 * @param _paramnum	����ԭ�͵Ĳ�������
	 * @param params ����ԭ�͵Ĳ������������б�
	 * @return ���ض�Ӧԭ�͵�index
	 */
	public int GetProtoIdxByShortyAndParams(String shorty, String rtype, int _paramnum, String[] params)
	{
		String modShorty = new String(shorty);
		modShorty = modShorty + rtype;
		for (int i = 0; i < _paramnum; i++) 
		{
			modShorty = modShorty + params[i];
		}
		return GetProtoIdxByModifiedShorty(modShorty);
	}
	
	/**
	 * ͨ������ĺ���ԭ����Ϣ��ȡ����ԭ�Ͷ�Ӧ��index
	 * @param _modifiedshorty ����ĺ���ԭ����Ϣ
	 * @return ��Ӧ��index
	 */
	public int GetProtoIdxByModifiedShorty(String _modifiedshorty)
	{
		return hModifiedShortyToProtoIdx.get(_modifiedshorty);
	}
	
	/**
	 * ͨ��ԭ�͵�index��ȡ��Ӧ��ԭ�͵�shorty��������index��StringTable�У�
	 * @param _protoidx ԭ�͵�index
	 * @return ��Ӧ��ԭ�͵�shorty��������index��StringTable�У�
	 */
	public int GetProtoShortyIdx(int _protoidx)
	{
		return aProtoItems.get(_protoidx).shortyIdx;
	}
	
	/**
	 * ͨ��ԭ�͵�index��ȡ��Ӧ��ԭ�͵ķ������͵�index��TypeTable�У�
	 * @param _protoidx ԭ�͵�index
	 * @return ��Ӧ��ԭ�͵ķ������͵�index��TypeTable�У�
	 */
	public int GetProtoReturnTypeIdx(int _protoidx)
	{
		return aProtoItems.get(_protoidx).returnTypeIdx;
	}
	
	/**
	 * ͨ��ԭ�͵�index��ȡ��Ӧ��ԭ�͵Ĳ�������
	 * @param _protoidx ԭ�͵�index
	 * @return ��Ӧ��ԭ�͵Ĳ�������
	 */
	public int GetProtoParamsNum(int _protoidx)
	{
		return aProtoItems.get(_protoidx).paramNum;
	}
	
	/**
	 * ͨ��ԭ�͵�index��ȡ��Ҫ�����offsetλ��
	 * @param _protoidx ԭ�͵�index
	 * @return ��Ҫ�����offsetλ��
	 */
	public int GetProtoParamBackPatchOffset(int _protoidx)
	{
		return aProtoItems.get(_protoidx).parametersOffBackPatchOffset;
	}
	
	/**
	 * ͨ��ԭ�͵�index�Ͳ�����index��ȡ�������͵�index��TypeTable�У�
	 * @param _protoidx ԭ�͵�index
	 * @param _paramidx ������index
	 * @return �������͵�index��TypeTable�У�
	 */
	public short GetProtoParamTypeIdx(int _protoidx, int _paramidx)
	{
		return aProtoItems.get(_protoidx).typeList.typeidx[_paramidx];
	}
	
}