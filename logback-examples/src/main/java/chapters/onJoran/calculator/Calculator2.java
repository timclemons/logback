/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2011, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package chapters.onJoran.calculator;

import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.spi.Pattern;
import ch.qos.logback.core.util.StatusPrinter;
import chapters.onJoran.SimpleConfigurator;


/**
 * This examples illustrates collaboration between multiple actions through the
 * common execution context stack.
 * 
 * It differs from Calculator1 in that it supports arbitrary nesting of 
 * computation elements.
 * 
 * You can test this application with the sample XML file <em>calculator3.xml</em>.
 * 
 * @author Ceki G&uuml;ulc&uuml;
 */
public class Calculator2 {
  public static void main(String[] args) throws Exception {
    Map<Pattern, Action> ruleMap = new HashMap<Pattern, Action>();
   
    
    // Note the wild card character '*', in the paterns, signifying any level 
    // of nesting.
    ruleMap.put(new Pattern("*/computation"), new ComputationAction2());

    ruleMap.put(new Pattern("*/computation/literal"), new LiteralAction());
    ruleMap.put(new Pattern("*/computation/add"), new AddAction());
    ruleMap.put(new Pattern("*/computation/multiply"), new MultiplyAction());
    
    Context context = new ContextBase();
    SimpleConfigurator simpleConfigurator = new SimpleConfigurator(ruleMap);
    // link the configurator with its context
    simpleConfigurator.setContext(context);

    try {
      simpleConfigurator.doConfigure(args[0]);
    } catch (JoranException e) {
      // Print any errors that might have occured.
      StatusPrinter.print(context);
    }
  }
}
