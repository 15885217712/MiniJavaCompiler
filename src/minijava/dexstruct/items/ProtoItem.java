package minijava.dexstruct.items;

/**
 * ProtoItem�����ݽṹ
 */
public class ProtoItem{
	public int shortyIdx;   //to string table
	public int returnTypeIdx;  // to type table
	public int parametersOffBackPatchOffset;

	public int paramNum;
	public String shorty;
	public String returnType;
	public String[] paramStrings;
	public String modifiedShorty;
	public TypeList typeList;
	/**
	 * ���캯������Ҫ������Ϣ
	 * @param _modifiedShorty ���������shorty�ַ���Ϣ
	 * @param _shorty shorty�ַ���
	 * @param _returnType ����������
	 * @param _paramNum ��������
	 * @param _paramStrings �����������б�
	 */
	public ProtoItem(String _modifiedShorty, String _shorty, String _returnType, int _paramNum, String[] _paramStrings)
	{
		modifiedShorty = _modifiedShorty;
		shorty = _shorty;
		returnType = _returnType;
		paramNum = _paramNum;
		if (paramNum != 0) 
		{
			typeList = new TypeList();
			typeList.size = paramNum;
			typeList.typeidx = new short[paramNum];
			paramStrings = _paramStrings;
		}
	}
}
