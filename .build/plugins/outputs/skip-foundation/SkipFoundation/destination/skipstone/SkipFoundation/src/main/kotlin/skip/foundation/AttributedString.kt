// Copyright 2023 Skip
//
// This is free software: you can redistribute and/or modify it
// under the terms of the GNU Lesser General Public License 3.0
// as published by the Free Software Foundation https://fsf.org

package skip.foundation

import skip.lib.*

class AttributedString {
    // Allow e.g. SwiftUI to access our state
    val string: String
    val markdownNode: MarkdownNode?

    constructor() {
        string = ""
        markdownNode = null
    }

    constructor(stringLiteral: String) {
        string = stringLiteral
        markdownNode = null
    }

    constructor(markdown: String, @Suppress("UNUSED_PARAMETER") unusedp_0: Nothing? = null) {
        string = markdown
        markdownNode = MarkdownNode.from(string = markdown)
    }

    constructor(localized: StringLocalizationValue, table: String? = null, bundle: Bundle? = null, locale: Locale? = null, comment: String? = null) {
        val keyAndValue = localized
        val key = keyAndValue.patternFormat // interpolated string: "Hello \(name)" keyed as: "Hello %@"
        val (_, locfmt, locnode) = (bundle?.localizedInfo(forKey = key, value = null, table = table) ?: Triple("", key.kotlinFormatString, MarkdownNode.from(string = key))).sref()
        // re-interpret the placeholder strings in the resulting localized string with the string interpolation's values
        this.string = locfmt.format(*keyAndValue.stringInterpolation.values.toTypedArray())
        this.markdownNode = locnode?.format(keyAndValue.stringInterpolation.values)
    }

    constructor(localized: String, table: String? = null, bundle: Bundle? = null, locale: Locale? = null, comment: String? = null) {
        val key = localized
        val (locstring, _, locnode) = (bundle?.localizedInfo(forKey = key, value = null, table = table) ?: Triple(key, "", MarkdownNode.from(string = key))).sref()
        this.string = locstring.sref()
        this.markdownNode = locnode.sref()
    }

    val description: String
        get() = string

    override fun equals(other: Any?): Boolean {
        if (other !is AttributedString) {
            return false
        }
        val lhs = this
        val rhs = other
        return lhs.string == rhs.string
    }

    override fun hashCode(): Int {
        var hasher = Hasher()
        hash(into = InOut<Hasher>({ hasher }, { hasher = it }))
        return hasher.finalize()
    }
    fun hash(into: InOut<Hasher>) {
        val hasher = into
        hasher.value.combine(string)
    }

    companion object {
    }
}

