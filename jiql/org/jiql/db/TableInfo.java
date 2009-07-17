/*
 Copyright (c) 2007-2009 WebAppShowCase DBA Appcloem (http://www.appcloem.com). All rights reserved.
Apache Software License 2.0
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
     this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in
     the documentation and/or other materials provided with the distribution.

  3. The names of the authors may not be used to endorse or promote products
     derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL WebAppShowCase
OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.jiql.db;
import java.util.*;
import java.sql.*;
import java.io.*;
import org.jiql.util.SQLParser;

public class TableInfo extends Vector  implements Serializable
{
public String getTableName(){
	//return ((Vector)elementAt(0)).elementAt(1).toString();
	return tn;
}
String tn = null;
public void setTableName(String t){
	tn = t;
}

public ColumnInfo getColumnInfo(int i){
	return (ColumnInfo)elementAt(i -1) ;
	// -1
}
public ColumnInfo getColumnInfo(String n){
	
	
	ColumnInfo ci = null;
	if (n.toLowerCase().startsWith("count(")){
		/*Vector v = new Vector();
		v.add(((Vector)elementAt(0)).elementAt(0).toString());
		v.add(getTableName());
		v.add(n);
		v.add("int");*/
		org.jiql.util.JGNameValuePairs nvp = new org.jiql.util.JGNameValuePairs();
		
		nvp.put("tablefield",n);
		nvp.put("tablefieldtype","int");
		ci = new ColumnInfo(nvp);

		return ci;
	}
	//ct = 1 <=
	for (int ct = 1;ct <= size();ct++)
	{
		ci = getColumnInfo(ct);
		//(ci.getName().equals(n) + " ci.getName().equals(n) YEA " + ":" + n + ":" + ci.getName());
		if (ci.getName().equals(n))
			return ci;
	}
	return null;
}

public void include(SQLParser sqp){
	//|| v.size() < 1(v + " TII " + v.contains("*"));
	Vector v = sqp.getSelectList();
	Vector inc = new Vector();
	ColumnInfo ci = null;

	if (v.contains("*") )
	{
		try{
		
		Vector co = sqp.getJiqlTableInfo().getFieldList();
		for (int ct = 0; ct < co.size();ct++)
	{
		ci = getColumnInfo(co.elementAt(ct).toString());
			inc.add(ci);
	}

		while (size() > 0)
	{
		removeElementAt(0);

	}
	while (inc.size() > 0)
	{
		add((ColumnInfo)inc.elementAt(0));
		inc.removeElementAt(0);
	}
	
	
		return;
		}catch (Exception e){
			tools.util.LogMgr.err("TableInfo.inc* " + e.toString());
		}
	}
Vector v2 = new Vector();
	while (v.size() > 0)
	{
		String n = (String)v.elementAt(0);
		v.removeElementAt(0);
		n = sqp.getRealColName(n);
		if (!v2.contains(n))
			v2.add(n);
	}

	v = v2;


//	 inc = new Vector();
//	 ci = null;

	while (size() > 0)
	{
		ci = (ColumnInfo)elementAt(0);
		removeElementAt(0);
		//(v + " CI.INCL " + ci.getName());
		if (v.contains(ci.getName()))
			inc.add(ci);
	}
	while (inc.size() > 0)
	{
		add((ColumnInfo)inc.elementAt(0));
		inc.removeElementAt(0);
	}
}

}

