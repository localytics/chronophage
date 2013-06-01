package com.localytics

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class ChronophageTest extends FlatSpec with ShouldMatchers {

  "Chronophage" should "parse iso dates" in {
    Chronophage.parseDateTime("2010-12-12T00:00:00-05:00").get.getHourOfDay should be(5)
  }

  "Chronophage" should "parse utc dates" in {
    Chronophage.parseDateTime("2010-12-12 01:00:00 UTC").get.getHourOfDay should be(1)
  }

  "Chronophage" should "not parse dates that don't start with a date" in {
    Chronophage.parseDateTime("2011-085d5-b24c-3f026299b51c") should be(None)
    Chronophage.parseDateTime("٢٠١١-٠٨-١١T٢١:١١:٢٧-00:00") should be(None)
  }

  "Chronophage" should "throw exception on null string" in {
    intercept[NullPointerException] {
      Chronophage.parseDateTime(null)
    }
  }

  "Chronophage" should "parse 12hr dates with am/pm at the end" in {
    Chronophage.parseDateTime("2010-12-12T01:00:00 a.m.-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 am-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 AM-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 A.M-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 nachm.-05:00").get.getHourOfDay should be(18)
    Chronophage.parseDateTime("2010-12-12T01:00:00 vorm.-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 nachm.+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 vorm.+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T01:00:00 nachm.-05:00").get.getHourOfDay should be(18)
    Chronophage.parseDateTime("2010-12-12T01:00:00 vorm.-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 nachm.+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 vorm.+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T01:00:00 אחה״צ-05:00").get.getHourOfDay should be(18)
    Chronophage.parseDateTime("2010-12-12T01:00:00 לפנה״צ-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 אחה\"צ-05:00").get.getHourOfDay should be(18)
    Chronophage.parseDateTime("2010-12-12T01:00:00 לפנה\"צ-05:00").get.getHourOfDay should be(6)
    Chronophage.parseDateTime("2010-12-12T01:00:00 หลังเที่ยง+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 ก่อนเที่ยง+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T01:00:00 Կե․+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 Առ․+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T01:00:00 попладне+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 претпладне+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T01:00:00 pēcpusdienā+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 priekšpusdienā+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T01:00:00 сл. об.+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 пр. об.+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T01:00:00 пп+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T01:00:00 дп+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-26T01:00:00 ព្រឹក+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-26T01:00:00 ល្ងាច+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-08-01T01:00:00 enne keskpäeva+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-08-01T01:00:00 pärast keskpäeva+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-31T01:00:00 μ.μ.+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-31T01:00:00 π.μ.+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-27T01:00:00 popiet+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-27T01:00:00 priešpiet+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-27T01:00:00 поподне+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-27T01:00:00 пре подне+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-27T01:00:00 после полудня+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-27T01:00:00 до полудня+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-27T01:00:00 மாலை+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-27T01:00:00 காலை+05:00").get.getHourOfDay should be(20)
  }

  "Chronophage" should "parse 12hr dates with am/pm at the start" in {
    Chronophage.parseDateTime("2010-12-12T午後01:00:00+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T午前01:00:00+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T오후 01:00:00+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T오전 01:00:00+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2010-12-12T오후01:00:00+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2010-12-12T오전01:00:00+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-31T下午01:00:00+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-27T上午01:00:00+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-07-31T下午 01:00:00+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-07-27T上午 01:00:00+05:00").get.getHourOfDay should be(20)
    Chronophage.parseDateTime("2011-11-18Tम.नं. 01:00:00+05:00").get.getHourOfDay should be(8)
    Chronophage.parseDateTime("2011-11-18Tम.पू. 01:00:00+05:00").get.getHourOfDay should be(20)
  }

  "Chronophage" should "parse 12hr dates using additional ams/pms" in {
    new Chronophage(additionalAMsAtEnd = Set("weirdam")).parseDateTime("2010-12-12T01:00:00 weirdam-05:00").get.getHourOfDay should be(6)
    new Chronophage(additionalPMsAtEnd = Set("weirdpm")).parseDateTime("2010-12-12T01:00:00 weirdpm-05:00").get.getHourOfDay should be(18)
    new Chronophage(additionalAMsAtStart = Set("weirdam")).parseDateTime("2010-12-12Tweirdam01:00:00-05:00").get.getHourOfDay should be(6)
    new Chronophage(additionalPMsAtStart = Set("weirdpm")).parseDateTime("2010-12-12Tweirdpm01:00:00-05:00").get.getHourOfDay should be(18)
  }

  "Chronophage" should "parse 12hr dates using additional ams/pms at the end" in {
    new Chronophage(additionalAMStrings = Set("weirdam")).parseDateTime("2010-12-12T01:00:00 weirdam-05:00").get.getHourOfDay should be(6)
    new Chronophage(additionalPMStrings = Set("weirdpm")).parseDateTime("2010-12-12T01:00:00 weirdpm-05:00").get.getHourOfDay should be(18)
  }

  "Chronophage" should "parse 12hr dates using additional ams/pms at the start" in {
    new Chronophage(additionalAMStrings = Set("weirdam")).parseDateTime("2010-12-12Tweirdam01:00:00-05:00").get.getHourOfDay should be(6)
    new Chronophage(additionalPMStrings = Set("weirdpm")).parseDateTime("2010-12-12Tweirdpm01:00:00-05:00").get.getHourOfDay should be(18)
  }

}
