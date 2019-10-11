package com.hines.james.akkatodo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

public class AkkaTodoApplicationTests {

	static ActorSystem system;

	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
	}

	@AfterClass
	public static void teardown() {
		TestKit.shutdownActorSystem(system);
		system = null;
	}

	@Test
	public void testGreeterActorSendingOfGreeting() {
		final TestKit testProbe = new TestKit(system);
		final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));

		helloGreeter.tell(new Greeter.WhoToGreet("Akka"), ActorRef.noSender());
		helloGreeter.tell(new Greeter.Greet(), ActorRef.noSender());
		Printer.Greeting greeting = testProbe.expectMsgClass(Printer.Greeting.class);

		assertEquals("Hello, Akka", greeting.message);
	}

}
