$.fn.serializeObject = function() {
	var obj = {};
	var count = 0;
	$.each(this.serializeArray(),
		function(i, o) {
			var n = o.name,
				v = o.value;
			count++;
			obj[n] = obj[n] === undefined ? v : $.isArray(obj[n]) ? obj[n].concat(v) : [obj[n], v];
		});
	return JSON.stringify(obj);
};

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
}

function getNowDateTime() {
	return new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate();
}

function formatDateTime(time) {
	return new Date(time).getFullYear() + "-" + (new Date(time).getMonth() + 1) + "-" + new Date(time).getDate();
}


$(document).ready(function() {
	$(".ShowBox div").click(function() {
		$(".ShowBox").hide();
	});

	$("#loading").click(function() {
		layer.load();
	});
});

var MaskUtil = (function() {
	var $mask, $maskMsg;

	var defMsg = "<img src='/public/img/wait.gif' width='100'/>";

	function init() {
		if (!$mask) {
			$mask = $("<div></div>")
				.css({
					'position': 'absolute'
					, 'left': '0'
					, 'top': '0'
					, 'width': '100%'
					, 'height': '100%'
					, 'opacity': '0.3'
					, 'filter': 'alpha(opacity=30)'
					, 'display': 'none'
					, 'background-color': '#ccc'
				})
				.appendTo("body");
		}
		if (!$maskMsg) {
			$maskMsg = $("<div></div>")
				.css({
					'position': 'absolute'
					, 'top': '40%'
					, 'margin-top': '-20px'
					, 'width': 'auto'
					, 'display': 'none'
					, 'background-color': '#ffffff'
					, 'font-size': '14px'
				})
				.appendTo("body");
		}
		$mask.css({ width: "100%", height: $(document).height() });
		var scrollTop = $(document.body).scrollTop();
		$maskMsg.css({
			left: ($(document.body).outerWidth(true) - 190) / 2
			, top: (($(window).height() - 45) / 2) + scrollTop
		});
	}
	return {
		mask: function(msg) {
			init();
			$mask.show();
			$maskMsg.html(msg || defMsg).show();
		}
		, unmask: function() {
			$mask.hide();
			$maskMsg.hide();
		}
	}
}());

function FilterButton() {
	$(".FilterBox").fadeToggle(500);
}

function checkSelect(type) {
	$("#FormContent").slideUp();
	$("#OpenForm").fadeIn();

	if (type === 1) {
		var i = false;
		$(".checkChild").each(function() {
			if ($(this).is(":checked")) {
				i = true;
			}
		});

		if (i) {
			$(".SelectButton").css("display", "inline-block");
		}
		else {
			$(".SelectButton").css("display", "none");
			$("#checkMaster").prop("checked", false);
		}
	}
	else {
		if ($("#checkMaster").prop('checked')) {
			$(".checkChild").prop('checked', true);
			if ($(".checkChild").prop('checked')) {
				$(".SelectButton").css("display", "inline-block");
			}
		}
		else {
			$(".checkChild").prop('checked', false);
			$(".SelectButton").css("display", "none");
		}
	}
};
