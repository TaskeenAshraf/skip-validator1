// Copyright 2023 Skip
//
// This is free software: you can redistribute and/or modify it
// under the terms of the GNU Lesser General Public License 3.0
// as published by the Free Software Foundation https://fsf.org

package skip.foundation

import skip.lib.*
import skip.lib.Array

import okhttp3.HttpUrl.Companion.toHttpUrl

class URLComponents: Sendable, MutableStruct {
    constructor() {
    }

    constructor(url: URL, resolvingAgainstBaseURL: Boolean) {
        val resolve = resolvingAgainstBaseURL
        this.url = url
    }

    constructor(string: String): this(string = string, encodingInvalidCharacters = true) {
    }

    constructor(string: String, encodingInvalidCharacters: Boolean) {
        val url_0 = (try { URL(string = string, encodingInvalidCharacters = encodingInvalidCharacters) } catch (_: NullReturnException) { null })
        if (url_0 == null) {
            throw NullReturnException()
        }
        this.url = url_0
    }

    var url: URL?
        get() {
            val string_0 = this.string
            if (string_0 == null) {
                return null
            }
            return (try { URL(string = string_0) } catch (_: NullReturnException) { null }).sref({ this.url = it })
        }
        set(newValue) {
            @Suppress("NAME_SHADOWING") val newValue = newValue.sref()
            this.scheme = newValue?.scheme
            this.host = newValue?.host(percentEncoded = false)
            this.port = newValue?.port
            this.path = newValue?.path(percentEncoded = false) ?: ""
            this.fragment = newValue?.fragment
            this.queryItems = URLQueryItem.from(newValue?.query(percentEncoded = false))
        }

    fun url(relativeTo: URL?): URL? {
        val base = relativeTo
        val string_1 = this.string
        if (string_1 == null) {
            return null
        }
        return (try { URL(string = string_1, relativeTo = base) } catch (_: NullReturnException) { null })
    }

    var string: String?
        get() {
            var string = ""
            scheme?.let { scheme ->
                string += scheme + ":"
            }
            host?.let { host ->
                if (scheme != null) {
                    string += "//"
                }
                string += host
                port?.let { port ->
                    string += ":${port}"
                }
            }
            string += path
            fragment?.let { fragment ->
                string += "#" + fragment
            }
            URLQueryItem.queryString(from = queryItems)?.let { query ->
                string += "?" + query
            }
            return if (string.isEmpty) null else string
        }
        set(newValue) {
            if (newValue != null) {
                this.url = (try { URL(string = newValue) } catch (_: NullReturnException) { null })
            } else {
                this.url = null
            }
        }

    var scheme: String? = null
        set(newValue) {
            willmutate()
            field = newValue
            didmutate()
        }
    var host: String? = null
        set(newValue) {
            willmutate()
            field = newValue
            didmutate()
        }
    var port: Int? = null
        set(newValue) {
            willmutate()
            field = newValue
            didmutate()
        }
    var path = ""
        set(newValue) {
            willmutate()
            field = newValue
            didmutate()
        }
    var fragment: String? = null
        set(newValue) {
            willmutate()
            field = newValue
            didmutate()
        }
    var queryItems: Array<URLQueryItem>? = null
        get() = field.sref({ this.queryItems = it })
        set(newValue) {
            @Suppress("NAME_SHADOWING") val newValue = newValue.sref()
            willmutate()
            field = newValue
            didmutate()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    var user: String?
        get() {
            fatalError()
        }
        set(newValue) {
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    var password: String?
        get() {
            fatalError()
        }
        set(newValue) {
        }

    var query: String?
        get() = URLQueryItem.queryString(from = queryItems)
        set(newValue) {
            queryItems = URLQueryItem.from(newValue)
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    var percentEncodedUser: String?
        get() {
            fatalError()
        }
        set(newValue) {
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    var percentEncodedPassword: String?
        get() {
            fatalError()
        }
        set(newValue) {
        }

    var percentEncodedHost: String?
        get() {
            return host?.addingPercentEncoding(withAllowedCharacters = CharacterSet.urlHostAllowed)
        }
        set(newValue) {
            host = newValue?.removingPercentEncoding
        }

    var encodedHost: String?
        get() = percentEncodedHost
        set(newValue) {
            percentEncodedHost = newValue
        }

    var percentEncodedPath: String
        get() {
            return path.split(separator = '/', omittingEmptySubsequences = false)
                .map { it -> it.addingPercentEncoding(withAllowedCharacters = CharacterSet.urlPathAllowed) ?: "" }
                .joined(separator = "/")
        }
        set(newValue) {
            path = newValue.removingPercentEncoding ?: ""
        }

    var percentEncodedQuery: String?
        get() = URLQueryItem.queryString(from = percentEncodedQueryItems)
        set(newValue) {
            this.query = newValue?.removingPercentEncoding
        }

    var percentEncodedFragment: String?
        get() {
            return fragment?.addingPercentEncoding(withAllowedCharacters = CharacterSet.urlFragmentAllowed)
        }
        set(newValue) {
            fragment = newValue?.removingPercentEncoding
        }

    var percentEncodedQueryItems: Array<URLQueryItem>?
        get() {
            return queryItems?.map { it ->
                URLQueryItem(name = it.name.addingPercentEncoding(withAllowedCharacters = CharacterSet.urlQueryAllowed) ?: "", value = it.value?.addingPercentEncoding(withAllowedCharacters = CharacterSet.urlQueryAllowed))
            }.sref({ this.percentEncodedQueryItems = it })
        }
        set(newValue) {
            @Suppress("NAME_SHADOWING") val newValue = newValue.sref()
            queryItems = newValue?.map { it ->
                URLQueryItem(name = it.name.removingPercentEncoding ?: it.name, value = it.value?.removingPercentEncoding)
            }
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfScheme: IntRange?
        get() {
            fatalError()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfUser: IntRange?
        get() {
            fatalError()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfPassword: IntRange?
        get() {
            fatalError()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfHost: IntRange?
        get() {
            fatalError()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfPort: IntRange?
        get() {
            fatalError()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfPath: IntRange?
        get() {
            fatalError()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfQuery: IntRange?
        get() {
            fatalError()
        }

    @Deprecated("This API is not yet available in Skip. Consider placing it within a #if !SKIP block. You can file an issue against the owning library at https://github.com/skiptools, or see the library README for information on adding support", level = DeprecationLevel.ERROR)
    val rangeOfFragment: IntRange?
        get() {
            fatalError()
        }

    private constructor(copy: MutableStruct) {
        @Suppress("NAME_SHADOWING", "UNCHECKED_CAST") val copy = copy as URLComponents
        this.scheme = copy.scheme
        this.host = copy.host
        this.port = copy.port
        this.path = copy.path
        this.fragment = copy.fragment
        this.queryItems = copy.queryItems
    }

    override var supdate: ((Any) -> Unit)? = null
    override var smutatingcount = 0
    override fun scopy(): MutableStruct = URLComponents(this as MutableStruct)

    override fun equals(other: Any?): Boolean {
        if (other !is URLComponents) return false
        return scheme == other.scheme && host == other.host && port == other.port && path == other.path && fragment == other.fragment && queryItems == other.queryItems
    }

    override fun hashCode(): Int {
        var result = 1
        result = Hasher.combine(result, scheme)
        result = Hasher.combine(result, host)
        result = Hasher.combine(result, port)
        result = Hasher.combine(result, path)
        result = Hasher.combine(result, fragment)
        result = Hasher.combine(result, queryItems)
        return result
    }

    companion object {
    }
}

@Suppress("MUST_BE_INITIALIZED")
class URLQueryItem: Sendable, MutableStruct {
    var name: String
        set(newValue) {
            willmutate()
            field = newValue
            didmutate()
        }
    var value: String? = null
        set(newValue) {
            willmutate()
            field = newValue
            didmutate()
        }

    constructor(name: String, value: String? = null) {
        this.name = name
        this.value = value
    }

    override var supdate: ((Any) -> Unit)? = null
    override var smutatingcount = 0
    override fun scopy(): MutableStruct = URLQueryItem(name, value)

    override fun equals(other: Any?): Boolean {
        if (other !is URLQueryItem) return false
        return name == other.name && value == other.value
    }

    override fun hashCode(): Int {
        var result = 1
        result = Hasher.combine(result, name)
        result = Hasher.combine(result, value)
        return result
    }

    companion object {

        internal fun from(string: String?): Array<URLQueryItem>? {
            if ((string == null) || string.isEmpty) {
                return null
            }
            try {
                val httpUrl_0 = ("http://skip.tools/?" + string).toHttpUrl()
                if (httpUrl_0 == null) {
                    return null
                }
                return (0..<httpUrl_0.querySize).map l@{ index ->
                    val name = httpUrl_0.queryParameterName(index)
                    val value = httpUrl_0.queryParameterValue(index)
                    return@l URLQueryItem(name = name, value = value)
                }
            } catch (error: Throwable) {
                @Suppress("NAME_SHADOWING") val error = error.aserror()
                return null
            }
        }

        internal fun queryString(from: Array<URLQueryItem>?): String? {
            val items = from
            if ((items == null) || items.isEmpty) {
                return null
            }
            var query = ""
            for (item in items.sref()) {
                val name = item.name
                val value = item.value ?: ""
                if (!query.isEmpty) {
                    query += "&"
                }
                query += name + "=" + value
            }
            return query
        }
    }
}

