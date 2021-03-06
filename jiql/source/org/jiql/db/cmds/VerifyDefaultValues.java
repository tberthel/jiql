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

package org.jiql.db.cmds;
import java.util.*;
import java.sql.*;
import java.io.*;
import org.jiql.util.*;
import tools.util.EZArrayList;
import org.jiql.db.*;
import org.jiql.db.objs.*;


public class VerifyDefaultValues extends DBCommand
{
public Object execute(SQLParser sqp)throws SQLException{
			//Hashtable ch = cgw.getConstraints(,sqp.getTable());
			jiqlTableInfo ti = sqp.getJiqlTableInfo();
			//jiqlDBMgr.get(sqp.getProperties()).getTableInfo(sqp.getTable(),true);
if (ti == null )return null;
Hashtable dv = ti.getDefaultValues();
//("vc1 " + ch);
			Hashtable inv = sqp.getHash();

			if (dv != null && dv.size() > 0)
			{
			
String pk = null;

			Enumeration en = dv.keys();
		while(en.hasMoreElements())
			{
			pk = (String)en.nextElement();
			if (inv.get(pk) == null)// || inv.get(pk).toString().toLowerCase().equals("null"))
			 inv.put(pk,dv.get(pk));
			}
			}

Vector ch = ti.getNotNulls();
String nn = null;
Object nnv = null;
//(nn + " getAutoIncrementInt 1 " + ch);
for (int ct = 0;ct < ch.size();ct++)
{
	nn = ch.elementAt(ct).toString();
	nnv = inv.get(nn);
//(nn + " getAutoIncrementInt 2 " + nnv);

	if (nnv == null || nnv.toString().equalsIgnoreCase("NULL"))
	{
			TableInfo tbi = sqp.getTableInfo();
//(nn + " getAutoIncrementInt 3 " + tbi);

			if (ti != null){
			
			ColumnInfo ci = tbi.getColumnInfo(nn);
//(nn + " getAutoIncrementInt 4 " + ti);

			if (ci != null){
				//(nn + " getAutoIncrementInt c " + ci.getColumnType() + ":" + ci.getTypeName() + ":" + Types.BIGINT );

				if (ci.isNumeric()){
				//(nn + " getAutoIncrementInt a " + sqp.getJiqlTableInfo().listAutoIncrements());

				if (sqp.getJiqlTableInfo().listAutoIncrements().contains(nn))
				{
					int incr = Gateway.get(sqp.getProperties()).getAutoIncrementInt(sqp,nn);
				//(nn + " getAutoIncrementInt b " + incr);
					sqp.getInsertParser().setAutoIncrementValue(incr);
					inv.put(nn,sqp.getInsertParser().getAutoIncrementValue());
				}
				else
				 inv.put(nn,0);
				}
				
			}
			}

	}	
}			

			
			
			return null;
}



}


