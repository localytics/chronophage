package com.localytics

import com.localytics.Chronophage._
import util.matching.Regex
import org.joda.time.DateTimeZone.UTC
import org.joda.time.format.{DateTimeFormat, ISODateTimeFormat}
import org.joda.time.DateTime

object Chronophage {

  val DEFAULT_AMS_AT_END = Set("am", "saaku", "vm", "an", "dop", "vorm",
    "atm", "a", "lb", "de", "fh", "m", "bn", "qn", "wd", "pre podne",
    "soodo", "dop", "sn", "pd", "fm", "hh", "brax kndaax", "sa", "el",
    "rn", "ap", "dopoludnia", "rytas", "pagi", "לפנה״צ", "ก่อนเที่ยง", "առ․",
    "претпладне", "дп", "priekšpusdienā", "пр об", "ព្រឹក", "enne keskpäeva",
    "priešpiet", "πμ", "לפנה\"צ", "காலை", "пре подне", "до полудня")

  val DEFAULT_PMS_AT_END = Set("pm", "carra", "nm", "ew", "odp", "nachm",
    "ptm", "p", "sn", "nam", "du", "eh", "pn", "wn", "wb", "popodne",
    "hawwaro", "pop", "gn", "md", "em", "ea", "baubau kndaax", "ch", "pl",
    "in", "ip", "popoludní", "popiet", "malam", "אחה״צ", "หลังเที่ยง", "կե․",
    "попладне", "пп", "pēcpusdienā", "сл об", "ល្ងាច", "pärast keskpäeva",
    "popiet", "μμ", "אחה\"צ", "மாலை", "поподне", "после полудня")

  val DEFAULT_AMS_AT_START = Set("午前", "오전", "上午", "मपू", "पूर्व मध्यान्ह", "ກ່ອນທ່ຽງ", "སྔ་དྲོ་")
  val DEFAULT_PMS_AT_START = Set("午後", "오후", "下午", "मनं", "उत्तर मध्यान्ह", "ຫລັງທ່ຽງ", "ཕྱི་དྲོ་")

  val UTC_GREP = """^(.+)(?: utc)$""".r
  val STARTS_WITH_DATE = """^(\d{4}-\d{2}-\d{2}).*$""".r

  val DATE_TIME_PARSER_ISO = ISODateTimeFormat.dateTimeParser.withZone(UTC)
  val DATE_TIME_PARSER_12HR_AT_END = DateTimeFormat.forPattern("yyyy-MM-dd'T'hh:mm:ss aZZ").withZone(UTC)
  val DATE_TIME_PARSER_12HR_AT_START = DateTimeFormat.forPattern("yyyy-MM-dd'T'ahh:mm:ssZZ").withZone(UTC)
  val DATE_TIME_PARSER_UTC = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(UTC)

  val default = new Chronophage()

  def parseDateTime(str: String): Option[DateTime] = default.parseDateTime(str)

}

class Chronophage(additionalAMStrings: Set[String] = Set(), additionalPMStrings: Set[String] = Set(),
                  additionalAMsAtEnd: Set[String] = Set(), additionalPMsAtEnd: Set[String] = Set(),
                  additionalAMsAtStart: Set[String] = Set(), additionalPMsAtStart: Set[String] = Set()) {

  val amsAtEnd = DEFAULT_AMS_AT_END ++ additionalAMStrings ++ additionalAMsAtEnd
  val pmsAtEnd = DEFAULT_PMS_AT_END ++ additionalPMStrings ++ additionalPMsAtEnd

  val amsAtStart = DEFAULT_AMS_AT_START ++ additionalAMStrings ++ additionalAMsAtStart
  val pmsAtStart = DEFAULT_PMS_AT_START ++ additionalPMStrings ++ additionalPMsAtStart

  val amAtEndGrep = new Regex( """^(\d{4}-\d{2}-\d{2}t\d{2}:\d{2}:\d{2} )(""" + amsAtEnd.reduceLeft(_ + "|" + _) + """)([-+]\d{2}:\d{2})$""")
  val pmAtEndGrep = new Regex( """^(\d{4}-\d{2}-\d{2}t\d{2}:\d{2}:\d{2} )(""" + pmsAtEnd.reduceLeft(_ + "|" + _) + """)([-+]\d{2}:\d{2})$""")

  val amAtStartGrep = new Regex( """^(\d{4}-\d{2}-\d{2}t)(""" + amsAtStart.reduceLeft(_ + " ?|" + _) + """ ?)(\d{2}:\d{2}:\d{2}[-+]\d{2}:\d{2})$""")
  val pmAtStartGrep = new Regex( """^(\d{4}-\d{2}-\d{2}t)(""" + pmsAtStart.reduceLeft(_ + " ?|" + _) + """ ?)(\d{2}:\d{2}:\d{2}[-+]\d{2}:\d{2})$""")

  def parseDateTime(str: String): Option[DateTime] = {
    if (str == null) throw new NullPointerException("Cannot parse date from null string.")
    str.toLowerCase.replace(".", "") match {
      case amAtEndGrep(prefix, am, suffix) =>
        Some(DATE_TIME_PARSER_12HR_AT_END.parseDateTime(prefix + "am" + suffix))
      case pmAtEndGrep(prefix, pm, suffix) =>
        Some(DATE_TIME_PARSER_12HR_AT_END.parseDateTime(prefix + "pm" + suffix))
      case amAtStartGrep(prefix, am, suffix) =>
        Some(DATE_TIME_PARSER_12HR_AT_START.parseDateTime(prefix + "am" + suffix))
      case pmAtStartGrep(prefix, pm, suffix) =>
        Some(DATE_TIME_PARSER_12HR_AT_START.parseDateTime(prefix + "pm" + suffix))
      case UTC_GREP(datetime) =>
        Some(DATE_TIME_PARSER_UTC.parseDateTime(datetime))
      case STARTS_WITH_DATE(_) =>
        Some(DATE_TIME_PARSER_ISO.parseDateTime(str))
      case _ =>
        None
    }
  }

}
