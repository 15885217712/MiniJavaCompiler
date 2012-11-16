package minijava.dexstruct;

/**
 * ULEB128�����İ�װ��
 */
public class UnsignedLEB128Package {
	private int result;
	private int nextOffset;
	
	UnsignedLEB128Package(int _result,int _nextOffset)
	{
		result = _result;
		nextOffset = _nextOffset;
	}
	
	/**
	 * ��ȡ��ǰɨ�赽��ULEB128��ֵ
	 * @return ��ǰɨ�赽��ULEB128��ֵ
	 */
	public int Result()
	{
		return result;
	}
	
	/**
	 * ��ȡ��һ��ULEB128������ʼoffset
	 * @return ��һ��ULEB128������ʼoffset
	 */
	public int NextOffset()
	{
		return nextOffset;
	}
}
