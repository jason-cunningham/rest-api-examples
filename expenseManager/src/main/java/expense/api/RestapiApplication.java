package expense.api;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import expense.das.DAO;
import expense.das.MongoDAOImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

/**
 * Created by jcunningham on 3/6/16.
 */

@RestController
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class
})
public class RestapiApplication {

    DAO dao = MongoDAOImpl.getMongoInstance();

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView jspRequest() throws Exception {
        return new ModelAndView("/merchantExpense.html");
    }

	@RequestMapping(value="/expense", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String createExpense(@RequestBody String expenseFormData) {
		String result = null;

		try {
			String newId = dao.create( (DBObject) JSON.parse(expenseFormData ));
            result = "Status: Successfully created expense record - " + newId;
		} catch (Exception e) {
			result = "Error: " + e.getMessage();
		}

		return result;
	}

	@RequestMapping(value="/expense/{id}", method= RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String updateExpense(@PathVariable String id, @RequestBody String expenseFormData) {
		String result = null;

		try {
            dao.update(id, (DBObject) JSON.parse(expenseFormData ));
            result = "Status: Update successful.";
		} catch (Exception e) {
			result = "Error: " + e.getMessage() + ".";
		}

		return result;
	}

    @RequestMapping(value="/expense/findAll", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String findExpense() {
        StringBuilder result = new StringBuilder();

        try {
            List<DBObject> data = dao.findAll();
            Collections.reverse(data);
            formatAsJSON(result, data);

        } catch (Exception e) {
            result.append("Error: " + e.getMessage() + ".");
        }

        if (result.length() < 1) {
            result.append("Status: No records found");
        }

        return result.toString().replace("$oid", "oid");
    }

    private void formatAsJSON(StringBuilder result, List<DBObject> data) {
        int i=1;
        result.append("[");

        for( DBObject object : data ) {
            result.append(object.toString());

            if (i++ < data.size()) {
                result.append(",");
            }
        }

        result.append("]");
    }

    @RequestMapping(value="/expense/{id}", method= RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String findExpense(@PathVariable String id) {
		StringBuilder result = new StringBuilder();

		try {
			List<DBObject> data = dao.find(id);

            if (data != null && !data.isEmpty()) {
                result.append(data.get(0).toString());
            }

		} catch (Exception e) {
			result.append("Error: " + e.getMessage() + ".");
		}

        if (result.length() < 1) {
            result.append("Status: No records found using - " + id + ".");
        }

		return result.toString();
	}

    @RequestMapping(value="/expense/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteExpense(@PathVariable String id) {
        StringBuilder result = new StringBuilder();

        try {
            dao.delete(id);
            result.append("Delete Request Successful");
        } catch (Exception e) {
            result.append("Error: " + e.getMessage());
        }

        return result.toString();
    }
}
