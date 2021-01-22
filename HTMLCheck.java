
import java.util.ArrayList;

public class HTMLCheck {

	public String checkHTML(String[] data) {

		ArrayList<String> blockTags = new ArrayList<String>(); //Saves tags on their own so they can be compared later

		for (String s : data) { //Loops thru each string in the array
			ArrayList<Integer> index = new ArrayList<Integer>(); //The array lists are cleared each loop
			ArrayList<String> tagName = new ArrayList<String>();

			if (s.length() > 0 && (s.charAt(0) == '<' && s.charAt(s.length() - 1) == '>')) {
				index.add(0); // Adds index of very first char which will be <

				index.add(s.indexOf('>')); // Finds first closing tag

				int tempIndex;

				if (index.get(index.size() - 1) != s.length()) { //if the current character index in the index array is not the last character
					tempIndex = s.indexOf('<', index.get(index.size() - 1)); //Find the start of the next tag starting from the last closing tag

					boolean startTag = false;
					char tag;

					while (tempIndex != -1) { //While new tags are found
						index.add(tempIndex);

						if (startTag) {
							tag = '<';
						}

						else {
							tag = '>';
						}
						tempIndex = s.indexOf(tag, index.get(index.size() - 1)); //Check for next tag if none return -1 aka false
						startTag = !startTag;  //Change state so tag var will be different
					}
				}

				for (int i = 0; i < index.size() - 1; i += 2) {
					tagName.add(s.substring(index.get(i) + 1, index.get(i + 1)));  //Adds tagname e.g. head to tagname array
				}
			}

			else {
				return "Not Valid";
			}

			if (tagName.size() > 1) {  //if tagsize > 1 then there are multiple tags on the same line e.g. <p></p>
				for (int i = 0; i < tagName.size() / 2; i++) {
					String addSlash = "/" + tagName.get(i);  //Add a slash to start of tag so its the same as end tag

					if (!(addSlash.equals(tagName.get(tagName.size() - i - 1)))) {  //Get tag from start and end of array and work inwards
						return "Not Valid";
					}
				}
			}

			else {
				blockTags.add(tagName.get(0));  //Add to blocktags array to compare later
			}
		}

		if (blockTags.size() > 0) {   //If array isnt empty

			if (blockTags.size() % 2 == 1) {  //If array size is odd then tags won't match
				return "Not Valid";
			}

			else {

				for (int i = 0; i < blockTags.size() / 2; i++) {  //Check start and end tags are the same
					String addSlash = "/" + blockTags.get(i);

					if (!(addSlash.equals(blockTags.get(blockTags.size() - i - 1)))) {
						return "Not Valid";
					}
				}
			}
		}

		return "Valid";

	}

	public static void main(String[] args) {

		String[] data = { "<html>", "<head>Title</head>", "<body>", "<p><span>Paragraph</span></p>", "</body>",
				"</html>" };  //This array passes in the input to be checked currently it is valid
		HTMLCheck ch = new HTMLCheck();
		String output = "Valid";

		output = ch.checkHTML(data);

		System.out.println(output);
	}
}