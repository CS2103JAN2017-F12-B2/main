//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NotesTest {

    @Test
    public void isValidNotes() {

        // invalid notes
        assertFalse(Notes.isValidNotes("")); // empty string
        assertFalse(Notes.isValidNotes(" ")); // space
        // valid notes
        assertTrue(Notes.isValidNotes("new")); // string
        assertTrue(Notes.isValidNotes("very important")); // string with space
        assertTrue(Notes.isValidNotes("must done by 17 Mar")); // string with
                                                               // space and
                                                               // integer
        assertTrue(Notes.isValidNotes("member: peter")); // string with symbol
    }
}
