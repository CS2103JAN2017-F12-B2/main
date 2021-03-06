package seedu.todoapp.logic.commands;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.Completion;
import seedu.todoapp.model.person.Deadline;
import seedu.todoapp.model.person.Name;
import seedu.todoapp.model.person.Notes;
import seedu.todoapp.model.person.Priority;
import seedu.todoapp.model.person.Start;
import seedu.todoapp.model.person.Task;
import seedu.todoapp.model.person.UniqueTaskList;
import seedu.todoapp.model.person.Venue;
import seedu.todoapp.model.tag.Tag;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * Adds a task to the ToDoApp.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the ToDoApp. "
            + "Parameters: NAME [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES]...\n"
            + "Example: " + COMMAND_WORD
            + " Buy Printer";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp";
    public static final String MESSAGE_INVALID_START_END = "The task deadline cannot be before the start time";

    private final Task toAdd;
    private final int idx; // Optional adding of index

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String start, String deadline,
                        Integer priority, Set<String> tags, String notes, String venue, String completion, int idx)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Start(start),
                new Deadline(deadline),
                new Priority(priority),
                new UniqueTagList(tagSet),
                new Notes(notes),
                new Completion(completion),
                new Venue(venue)
        );
        this.idx = idx;
    }

    //@@author A0114395E
    @Override
    public CommandResult execute() throws CommandException, ParseException {
        assert model != null;
        try {
            // Ensure that Deadline is not before Start
            if (this.toAdd.getStart().hasDate() && this.toAdd.getDeadline().hasDate()
                    && this.toAdd.getStart().getDate().after(this.toAdd.getDeadline().getDate())) {
                throw new UniqueTaskList.TaskInvalidTimestampsException();
            }
            if (this.idx >= 0) {
                model.addTask(toAdd, idx);
            } else {
                model.addTask(toAdd);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (UniqueTaskList.TaskInvalidTimestampsException e) {
            throw new CommandException(MESSAGE_INVALID_START_END);
        }

    }

}
