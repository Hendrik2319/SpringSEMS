package net.schwarzbaer.java.spring.sems.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LdapInspectController {
    
	@Autowired
	private LdapTemplate ldapTemplate;

	private static final String[] attributes = new String[] {
		"dn", "uid", "dc", "ou", "cn", "sn", "objectclass", "uniqueMember", "userPassword"
	};

	@GetMapping("/ldap")
	public String showLdapView(Model model) {
		model.addAttribute("attributes", attributes);
		model.addAttribute("strList", getNames());
		model.addAttribute("allData", getAllData());
		return "ldap_view";
	}

	private List<String> getNames() {
		LdapQuery query = query()
			.base("dc=springframework,dc=org")
			.attributes("cn", "sn")
			.where("objectclass").is("person")
		//	.and("sn").is(lastName)
			;
		return ldapTemplate.search(query,
			new AttributesMapper<String>() {
				@Override public String mapFromAttributes(Attributes attrs) throws NamingException {
					Attribute attrCn = attrs.get("cn");
					return (String) attrCn.get();
				}
			}
		);
	}

	private List<GenericAttributesMap> getAllData() {
		LdapQuery query = query()
			.base("dc=springframework,dc=org")
			.attributes(attributes)
			.where("objectclass").is("top")
		//	.and("sn").is(lastName)
			;
		return ldapTemplate.search(query, GenericAttributesMap::new );
	}

	public static class GenericAttributesMap
	{
		private final Attributes attrs;
		private GenericAttributesMap(Attributes attrs) { this.attrs = Objects.requireNonNull(attrs); }

		public GenericAttribute get(String attrID) { return new GenericAttribute(attrs.get(attrID)); }

		public static class GenericAttribute
		{
			private final Attribute attr;
			private GenericAttribute(Attribute attr) { this.attr = attr; }

			public boolean isNull() { return attr==null; }
			public int size() { return attr!=null ? attr.size() : 0; }

			public GenericValue get() {
				if (attr!=null)
					try
					{
						return new GenericValue(attr.get());
					}
					catch (NamingException e)
					{
						// e.printStackTrace();
						System.err.printf("NamingException while retrieving value from attribute \"%s\".%n", attr.getID());
					}
				return new GenericValue(null);
			}

			public Vector<GenericValue> getAll() {
				Vector<GenericValue> list = new Vector<>();
				if (attr!=null)
					try
					{
						NamingEnumeration<?> values = attr.getAll();
						while (values.hasMore())
							list.add(new GenericValue(values.next()));
					}
					catch (NamingException e)
					{
						//e.printStackTrace();
						System.err.printf("NamingException while retrieving all values from attribute \"%s\".%n", attr.getID());
					}
				return list;
			}

		}

		public static class GenericValue
		{
			private final Object obj;
			private GenericValue(Object obj) { this.obj = obj; }

			public boolean isNull() { return obj==null; }
			public String getValueStr()
			{
				if (obj == null) return "<null>";
				if (obj instanceof byte  []) return Arrays.toString((byte  [])obj);
				if (obj instanceof int   []) return Arrays.toString((int   [])obj);
				if (obj instanceof long  []) return Arrays.toString((long  [])obj);
				if (obj instanceof float []) return Arrays.toString((float [])obj);
				if (obj instanceof double[]) return Arrays.toString((double[])obj);
				return obj.toString();
			}

			public boolean isByteArr()
			{
				return obj instanceof byte[];
			}

			public String getByteArrAsString()
			{
				if (obj instanceof byte[]) return String.format("\"%s\"", new String((byte[])obj));
				return getValueStr();
			}


			public String getClassKey()
			{
				if (obj == null) return "-";
				if (obj instanceof String) return "\"";
				return "C";
			}
			public String getClassStr()
			{
				if (obj == null)
					return "<null>";

				String str = obj.getClass().getCanonicalName();

				/* 
				if (obj instanceof Iterable<?>) {
					Iterable<?> arr = (Iterable<?>)obj;
					for (Object obj2 : arr) {
						if (obj2 == null)
							str += String.format("%n-> <null>");
						else
							str += String.format("%n-> %s", obj2.getClass().getName());
					}
				}
				 */

				return str;
			}

		}
	}
}
