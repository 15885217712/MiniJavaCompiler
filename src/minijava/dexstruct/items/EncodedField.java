package minijava.dexstruct.items;

/**
 * EncodedField�����ݽṹ
 */
public class EncodedField
{
	public int fieldIdx;
	public int accessFlag;
	
	public String modifiedField; //�������� fieldIdx
	
	/**
	 * ���캯��
	 * @param _class ��������
	 * @param _type ������
	 * @param _fieldname ������
	 * @param _accessFlag ����Ȩ��
	 */
	public EncodedField(String _class, String _type, String _fieldname, int _accessFlag)
	{
		modifiedField = new String(_class + _type + _fieldname);
		accessFlag = _accessFlag;
	}
}