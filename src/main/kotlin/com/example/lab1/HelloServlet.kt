package com.example.lab1

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.File

@WebServlet(name = "askServlet", value = ["/ask"])
class HelloServlet : HttpServlet() {
    private lateinit var message: String

    override fun init() {
        message = "Hello World!"
    }

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val key = request.getParameter ("key")
        val value = request.getParameter ("value").toInt()
        val test = request.getParameter ("test").toBoolean()
        var printString = ""

        if (test) {
            printString = "<h1>Test is passed (True)</h1>"
        } else {
            val path = servletContext.getRealPath("") + File.pathSeparator + "database.txt"
            val file = File(path)

            for (i in 0 until value) {
                file.appendText("$key ")
            }

            file.appendText("\n")
            printString = "<h1>Content of the repository:</h1>"
            printString += "<ul><b>Key:</b>$key</ul>"
            printString += "<ul><b>Value:</b>$value</ul>"
            printString += "<ul><b>Test:</b>$test</ul>"
        }

        response.writer.use{ out -> out.println("<html><body>" + printString + "</body></html>")}

        servletContext.log("METHOD: ${request.getMethod()} IP: ${request.getRemoteAddr()} USER-AGENT: ${request.getHeader("user-agent")} PARAMETERS: ${request.getParameterMap()}")
    }

    override fun destroy() {
    }
}