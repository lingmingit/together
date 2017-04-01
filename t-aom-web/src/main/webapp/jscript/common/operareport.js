/**
 * 分录合计函数功能重载<p>
 */
Ext.override(Ext.ux.grid.GridSummary.Calculations = {
	sum : function(v, record, colName, data, rowIdx) {
		if (v == null || v == "" || v == "null" || v == "￥" || v == '$') {
			v = 0;
		}
		var num = data[colName];
		var reg = new RegExp(",|￥|$", "g");
		if (typeof num != 'number') {
			num = parseFloat(num.replace(reg, ''));
		}
		if (typeof v != 'number') {
			v = parseFloat(v.replace(reg, ''));
			return v + (num);
		}
		return v + (num || 0);
	},
	count : function(v, record, colName, data, rowIdx) {
		return data[colName + 'count'] ? ++data[colName + 'count'] : (data[colName + 'count'] = 1);
	},

	max : function(v, record, colName, data, rowIdx) {
		var v = record.data[colName];
		var max = data[colName + 'max'] === undefined ? (data[colName + 'max'] = v) : data[colName + 'max'];
		return v > max ? (data[colName + 'max'] = v) : max;
	},

	min : function(v, record, colName, data, rowIdx) {
		var v = record.data[colName];
		var min = data[colName + 'min'] === undefined ? (data[colName + 'min'] = v) : data[colName + 'min'];
		return v < min ? (data[colName + 'min'] = v) : min;
	},

	average : function(v, record, colName, data, rowIdx) {
		var c = data[colName + 'count'] ? ++data[colName + 'count'] : (data[colName + 'count'] = 1);
		var t = (data[colName + 'total'] = ((data[colName + 'total'] || 0) + (record.data[colName] || 0)));
		return t === 0 ? 0 : t / c;
	}
});