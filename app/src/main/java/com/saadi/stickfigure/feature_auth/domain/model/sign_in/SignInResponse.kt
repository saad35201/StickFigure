package com.saadi.stickfigure.feature_auth.domain.model.sign_in

import com.fasterxml.jackson.annotation.JsonProperty

data class SignInResponse(

	@field:JsonProperty("user")
	val user: User? = null,

	@field:JsonProperty("token")
	val token: String? = null
)

data class Country(

	@field:JsonProperty("name")
	val name: String? = null,

	@field:JsonProperty("phonecode")
	val phonecode: String? = null,

	@field:JsonProperty("id")
	val id: String? = null
)

data class User(

	@field:JsonProperty("country")
	val country: Country? = null,

	@field:JsonProperty("cover_photo")
	val coverPhoto: Any? = null,

	@field:JsonProperty("user_name")
	val userName: String? = null,

	@field:JsonProperty("am_i_being_followed")
	val amIBeingFollowed: Boolean? = null,

	@field:JsonProperty("verified")
	val verified: Boolean? = null,

	@field:JsonProperty("photo")
	val photo: Any? = null,

	@field:JsonProperty("bio")
	val bio: String? = null,

	@field:JsonProperty("language_id")
	val languageId: Any? = null,

	@field:JsonProperty("total_likes")
	val totalLikes: Int? = null,

	@field:JsonProperty("total_posts")
	val totalPosts: Int? = null,

	@field:JsonProperty("total_followers")
	val totalFollowers: Int? = null,

	@field:JsonProperty("balance")
	val balance: Int? = null,

	@field:JsonProperty("total_creations")
	val totalCreations: Int? = null,

	@field:JsonProperty("phone")
	val phone: String? = null,

	@field:JsonProperty("hobbies")
	val hobbies: String? = null,

	@field:JsonProperty("dob")
	val dob: String? = null,

	@field:JsonProperty("name")
	val name: String? = null,

	@field:JsonProperty("id")
	val id: String? = null,

	@field:JsonProperty("am_i_following")
	val amIFollowing: Boolean? = null,

	@field:JsonProperty("total_products")
	val totalProducts: Int? = null,

	@field:JsonProperty("email")
	val email: String? = null,

	@field:JsonProperty("facebook")
	val facebook: String? = null,

	@field:JsonProperty("instagram")
	val instagram: String? = null,

	@field:JsonProperty("twitter")
	val twitter: String? = null,

	@field:JsonProperty("store_name")
	val store_name: String? = null,

	@field:JsonProperty("total_following")
	val totalFollowing: Int? = null,

	@field:JsonProperty("total_shares")
	val totalShares: Int? = null,

	@field:JsonProperty("available_amount")
	val availableAmount: Int? = null,

	@field:JsonProperty("dob_visibility")
	val dob_visibility: Int? = null,

	@field:JsonProperty("payments_enabled")
	val paymentsEnabled: Boolean? = null,

	@field:JsonProperty("is_already_request_sent")
	val isAlreadyRequestSent: Boolean? = null,

	@field:JsonProperty("is_blocked")
	val is_blocked: Boolean? = null


)
