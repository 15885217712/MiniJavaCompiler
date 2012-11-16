package minijava.dexstruct;

import java.util.*;

import minijava.dexstruct.items.MethodItem;

/**
 * MethodTable�ε����ݴ洢�ṹ
 */
public class DexMethodTable {
	private int size;
	private ArrayList<MethodItem> aMethodItems;
	private HashSet<String> hModifiedMethodsSet;
	private HashMap<String, Integer> hModifiedMethodStringToMethodIdx;
	private HashMap<Integer, String> hMethodIdxToModifiedMethodString;
	
	private DexStringTable stringTable;
	private DexTypeTable typeTable;
	private DexProtoTable protoTable;

	/**
	 * ���캯������Ҫ�������table�����ã���Щ��Ϣ�ᱻʹ�õ�
	 * @param _stringTable
	 * @param _typeTable
	 * @param _protoTable
	 */
	public DexMethodTable(DexStringTable _stringTable, DexTypeTable _typeTable, DexProtoTable _protoTable) 
	{
		stringTable = _stringTable;
		typeTable = _typeTable;
		protoTable = _protoTable;
		aMethodItems = new ArrayList<MethodItem>();
		hModifiedMethodsSet = new HashSet<String>();
		hMethodIdxToModifiedMethodString = new HashMap<Integer, String>();
		hModifiedMethodStringToMethodIdx = new HashMap<String, Integer>();
	}
	
	/**
	 * ���һ������
	 * @param _className ������������
	 * @param _methodName ������
	 * @param _returnType ��������ֵ������
	 * @param _paramNum �����������˴���0ʱ��_params����Ϊnull
	 * @param _params �����������б����ﴫ���������ã���ֱ��ʹ�õ����ö����Ǹ��ƣ�������޸Ļ���������Ӱ��
	 */
	public void AddMethod(String _className, String _methodName, String _returnType, int _paramNum, String[] _params)
	{
		String modifiedMethod = new String(_className + _methodName);
		if(hModifiedMethodsSet.add(modifiedMethod))
		{
			String shorty = GenerateShorty(_returnType, _paramNum, _params);
			String modShorty = new String(shorty);
			modShorty = modShorty + _returnType;
			for (int i = 0; i < _paramNum; i++) 
			{
				modShorty = modShorty + _params[i];
			}
			typeTable.AddType(_className);
			protoTable.AddProto(shorty, _returnType, _paramNum, _params);
			stringTable.AddString(_methodName);
			aMethodItems.add(new MethodItem(_className, _methodName, shorty, modShorty, modifiedMethod));
		}
	}
	
	/**
	 * ���һ������������������⣬GUI֧�ֵ�������Ҫ��
	 * @param _className ������������
	 * @param _methodName ������
	 * @param _returnType ��������ֵ������
	 * @param _paramNum �����������˴���0ʱ��_params����Ϊnull
	 * @param _params �����������б����ﴫ���������ã���ֱ��ʹ�õ����ö����Ǹ��ƣ�������޸Ļ���������Ӱ��
	 */
	public void LCAddMethod(String _className, String _methodName, String _returnType, int _paramNum, String[] _params)
	{
		String modifiedMethod = new String(_className + _methodName);
		for (int i = 0; i < _paramNum; i++) 
		{
			modifiedMethod += _params[i];
		}
		if(hModifiedMethodsSet.add(modifiedMethod))
		{
			String shorty = GenerateShorty(_returnType, _paramNum, _params);
			String modShorty = new String(shorty);
			modShorty = modShorty + _returnType;
			for (int i = 0; i < _paramNum; i++) 
			{
				modShorty = modShorty + _params[i];
			}
			typeTable.AddType(_className);
			protoTable.AddProto(shorty, _returnType, _paramNum, _params);
			stringTable.AddString(_methodName);
			aMethodItems.add(new MethodItem(_className, _methodName, shorty, modShorty, modifiedMethod));
		}
	}
	
	/**
	 * ����Ҫ�� StringTable �� TypeTable �� ProtoTable ������ Serilize() �������
	 */
	public void Serilize()
	{
		for (int i = 0; i < aMethodItems.size(); i++) 
		{
			aMethodItems.get(i).classIdx = (short)typeTable.GetTypeIdx(aMethodItems.get(i).className);
			aMethodItems.get(i).protoIdx = (short)protoTable.GetProtoIdxByModifiedShorty(aMethodItems.get(i).modifiedShorty);
			aMethodItems.get(i).nameIdx = stringTable.GetStringIdx(aMethodItems.get(i).methodName);
		}
		
		for (int i = 0; i < aMethodItems.size()-1; i++) 
		{
			for (int j = i; j < aMethodItems.size(); j++) 
			{
				if (aMethodItems.get(i).classIdx > aMethodItems.get(j).classIdx) 
				{
					MethodItem tmp = aMethodItems.get(i);
					aMethodItems.set(i, aMethodItems.get(j));
					aMethodItems.set(j, tmp);
				}
				else if (aMethodItems.get(i).classIdx == aMethodItems.get(j).classIdx) 
				{
					if (aMethodItems.get(i).nameIdx > aMethodItems.get(j).nameIdx) 
					{
						MethodItem tmp = aMethodItems.get(i);
						aMethodItems.set(i, aMethodItems.get(j));
						aMethodItems.set(j, tmp);
					}
					else if (aMethodItems.get(i).nameIdx == aMethodItems.get(j).nameIdx) 
					{
						if (aMethodItems.get(i).protoIdx > aMethodItems.get(j).protoIdx) 
						{
							MethodItem tmp = aMethodItems.get(i);
							aMethodItems.set(i, aMethodItems.get(j));
							aMethodItems.set(j, tmp);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < aMethodItems.size(); i++) 
		{
			hMethodIdxToModifiedMethodString.put(i, aMethodItems.get(i).modifiedMethod);
			hModifiedMethodStringToMethodIdx.put(aMethodItems.get(i).modifiedMethod, i);
		}
		
		size = aMethodItems.size();
	}
	
	/**
	 * ��ȡ��һ������Ŀ��
	 * @return ��һ���е���Ŀ��
	 */
	public int GetSize()
	{
		return size;
	}
	
	/**
	 * ͨ����������Ϣ����ȡ������Ӧ��index
	 * @param _className ������������
	 * @param _methodName ������
	 * @return ������Ӧ��index
	 */
	public int GetMethodIdxByInformation(String _className, String _methodName)
	{
		String modifiedMethod = new String(_className + _methodName);
		return GetMethodIdxByModifiedMethod(modifiedMethod);
	}
	
	/**
	 * ͨ������ķ���������ȡ������Ӧ��index
	 * @param _modifiedMethod ��������ķ�����
	 * @return ������Ӧ��index
	 */
	public int GetMethodIdxByModifiedMethod(String _modifiedMethod)
	{
		return hModifiedMethodStringToMethodIdx.get(_modifiedMethod);
	}
	
	/**
	 * ͨ��������index��ȡ��Ӧ�������������index��TypeTable�У�
	 * @param idx ����index
	 * @return ��Ӧ�������������index��TypeTable�У�
	 */
	public short GetMethodClassIdx(int idx)
	{
		return aMethodItems.get(idx).classIdx;
	}
	
	/**
	 * ͨ��������index��ȡ��Ӧ������ԭ�͵�index��ProtoTable�У�
	 * @param idx ����index
	 * @return ��Ӧ������ԭ�͵�index��ProtoTable�У�
	 */
	public short GetMethodProtoIdx(int idx)
	{
		return aMethodItems.get(idx).protoIdx;
	}
	
	/**
	 * ͨ��������index��ȡ��Ӧ���������Ƶ�index��StringTable�У�
	 * @param idx ����index
	 * @return ��Ӧ���������Ƶ�index��StringTable�У�
	 */
	public int GetMethodNameIdx(int idx)
	{
		return aMethodItems.get(idx).nameIdx;
	}

	/**
	 * ͨ����������Ϣ�����ɶ�Ӧproto��shorty
	 * @param _returnType �����ķ�����������
	 * @param _paramNum ������������
	 * @param _params �����������������б�
	 * @return ��Ӧproto��shorty�ַ���
	 */
	private String GenerateShorty(String _returnType, int _paramNum, String[] _params)
	{
		String retString = new String("");
		if(_returnType.startsWith("[") || _returnType.startsWith("L"))
			retString = retString + "L";
		else
			retString = retString + _returnType;
		
		for (int i = 0; i < _paramNum; i++) 
		{
			if(_params[i].startsWith("[") || _params[i].startsWith("L"))
				retString = retString + "L";
			else
				retString = retString + _params[i];
		}
		
		return retString;
	}
}