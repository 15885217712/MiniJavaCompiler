package minijava.dexstruct.items;

/**
 * CodeItem�����ݽṹ
 */
public class CodeItem
{
	public short registersSize;
	public short insSize;
	public short outsSize;
	public short triedSize = 0;
	public int debugInfoOff = 0;
	public int insnsSize;
	public byte[] code;
	/**
	 * ���캯������Ҫ������Ϣ����
	 * @param _registersSize ʹ�üĴ�������
	 * @param _insSize ʹ�õ���ڼĴ�������
	 * @param _outsSize ����ʹ�õļĴ�������
	 * @param _insnsSize �ֽ��볤�ȣ���2Byte�ƣ�
	 * @param _code �ֽ�������
	 */
	public CodeItem(short _registersSize, short _insSize, short _outsSize, int _insnsSize, byte[] _code)
	{
		registersSize = _registersSize;
		insSize = _insSize;
		outsSize = _outsSize;
		insnsSize = _insnsSize;
		code = new byte[insnsSize*2];
		for (int i = 0; i < _code.length; i++) 
		{
			code[i] = _code[i];
		}
	}
}