(function () {
  "use strict";
  var e = {
      8056: function (e, t, n) {
        var o = n(9242),
          s = n(3396);
        const i = {
            style: {
              height: "100%",
              width: "100%"
            }
          },
          a = {
            class: "grid-content bg-purple"
          },
          l = (0, s._)("i", {
            class: "el-icon-upload"
          }, null, -1),
          r = (0, s._)("div", {
            class: "el-upload__text"
          }, [(0, s.Uk)("将文件拖到此处，或"), (0, s._)("em", null, "点击上传")], -1),
          u = (0, s._)("div", {
            class: "el-upload__tip"
          }, "只能上传jpg/png文件,且不超过2M", -1),
          c = {
            class: "grid-content bg-purple-light"
          },
          d = {
            class: "b-button-div"
          },
          p = {
            class: "s-button-div"
          },
          h = {
            class: "grid-content bg-purple"
          };

        function g(e, t, n, o, g, m) {
          const f = (0, s.up)("el-upload"),
            v = (0, s.up)("el-col"),
            w = (0, s.up)("el-button"),
            y = (0, s.up)("el-input"),
            x = (0, s.up)("el-row");
          return (0, s.wg)(), (0, s.iD)("div", i, [(0, s.Wm)(x, {
            gutter: 10,
            type: "flex",
            class: "row-bg el-row-two",
            justify: "center"
          }, {
            default: (0, s.w5)((() => [(0, s.Wm)(v, {
              xs: 6,
              sm: 6,
              md: 6,
              xl: 6
            }, {
              default: (0, s.w5)((() => [(0, s._)("div", a, [(0, s.Wm)(f, {
                class: "upload-demo",
                drag: "",
                action: " ",
                "list-type": "picture",
                ref: "upload",
                "on-change": m.handleChange,
                "auto-upload": !1,
                limit: g.limit,
                "on-exceed": m.handleExceed,
                multiple: !1
              }, {
                default: (0, s.w5)((() => [l, r, u])),
                _: 1
              }, 8, ["on-change", "limit", "on-exceed"])])])),
              _: 1
            }), (0, s.Wm)(v, {
              xs: 3,
              sm: 3,
              md: 3,
              xl: 4
            }, {
              default: (0, s.w5)((() => [(0, s._)("div", c, [(0, s._)("div", d, [(0, s._)("div", p, [(0, s.Wm)(w, {
                style: {
                  "margin-top": "10px"
                },
                type: "primary",
                class: "s-button",
                onClick: m.submitUpload,
                loading: g.loading
              }, {
                default: (0, s.w5)((() => [(0, s.Uk)(" 提取文本 ")])),
                _: 1
              }, 8, ["onClick", "loading"])])])])])),
              _: 1
            }), (0, s.Wm)(v, {
              xs: 6,
              sm: 6,
              md: 6,
              xl: 6
            }, {
              default: (0, s.w5)((() => [(0, s._)("div", h, [(0, s._)("div", null, [(0, s.Wm)(y, {
                type: "textarea",
                "show-word-limit": !0,
                rows: 10,
                placeholder: "提取文本内容",
                modelValue: g.text,
                "onUpdate:modelValue": t[0] || (t[0] = e => g.text = e)
              }, null, 8, ["modelValue"]), (0, s.Wm)(w, {
                type: "text",
                class: "copyText",
                disabled: g.show,
                onClick: t[1] || (t[1] = e => m.copyText(g.text))
              }, {
                default: (0, s.w5)((() => [(0, s.Uk)("复制")])),
                _: 1
              }, 8, ["disabled"])])])])),
              _: 1
            })])),
            _: 1
          })])
        }
        var m = n(4161),
          f = {
            data() {
              return {
                fileList: [],
                text: "",
                limit: 1,
                loading: !1,
                show: !0
              }
            },
            components: {},
            methods: {
              handleChange(e, t) {
                this.fileList = t, console.log(this.fileList[0]);
                const n = "image/jpeg" === t[0].raw.type || "image/png" === t[0].raw.type,
                  o = t[0].raw.size / 1024 / 1024 < 2;
                return n || (this.$message.error("上传图片格式只能是 jpg或png! 文件已清除"), this.$refs.upload.clearFiles(), console.log("不合要求的文件已清除")), o || (this.$message.error("上传图片大小不能超过 2MB! 文件已清除"), this.$refs.upload.clearFiles(), console.log("不合要求的文件已清除")), n && o
              },
              async submitUpload() {
                if (0 == this.fileList.length) return this.$message({
                  message: "图片不能为空",
                  type: "warning"
                });
                this.loading = !0;
                const e = new FormData;
                e.append("file", this.fileList[0].raw);
                try {
                  m.Z.post("http://120.78.161.175:8001/getOcrText", e, {
                    timeout: 8500
                  }).then((e => {
                    1e4 == e.data.status || null != e.data.data ? (this.open2(), this.text = e.data.data, this.loading = !1, this.show = !1) : (this.loading = !1, this.open4())
                  }))
                } catch (t) {
                  "ECONNABORTED" === t.code || "Network Error" === t.message || t.message.includes("timeout") ? (this.open5(), this.loading = !1) : this.open4()
                }
              },
              handleExceed() {
                this.open3()
              },
              copyText(e) {
                if ("" != this.text) {
                  const t = document.createElement("textarea");
                  t.setAttribute("readonly", "readonly"), t.value = e, document.body.appendChild(t), t.setSelectionRange(0, t.value.length), t.select(), document.execCommand("copy"), document.body.removeChild(t) ? this.$message({
                    type: "success",
                    message: "复制成功"
                  }) : this.$message({
                    type: "error",
                    message: "复制失败"
                  })
                } else this.$message("暂无文本可复制")
              },
              open1() {
                this.$message("这是一条消息提示")
              },
              open2() {
                this.$message({
                  message: "提取成功",
                  type: "success"
                })
              },
              open3() {
                this.$message({
                  message: "只能上传一张图片喔",
                  type: "warning"
                })
              },
              open4() {
                this.$message.error("提取失败")
              },
              open5() {
                this.$message.error("请求超时，请稍后重试")
              }
            }
          },
          v = n(89);
        const w = (0, v.Z)(f, [
          ["render", g]
        ]);
        var y = w,
          x = n(82),
          b = (n(2834), n(50));
        (0, o.ri)(y).use(x.Z, {
          locale: b.Z
        }).mount("#app")
      }
    },
    t = {};

  function n(o) {
    var s = t[o];
    if (void 0 !== s) return s.exports;
    var i = t[o] = {
      exports: {}
    };
    return e[o].call(i.exports, i, i.exports, n), i.exports
  }
  n.m = e,
    function () {
      var e = [];
      n.O = function (t, o, s, i) {
        if (!o) {
          var a = 1 / 0;
          for (c = 0; c < e.length; c++) {
            o = e[c][0], s = e[c][1], i = e[c][2];
            for (var l = !0, r = 0; r < o.length; r++)(!1 & i || a >= i) && Object.keys(n.O).every((function (e) {
              return n.O[e](o[r])
            })) ? o.splice(r--, 1) : (l = !1, i < a && (a = i));
            if (l) {
              e.splice(c--, 1);
              var u = s();
              void 0 !== u && (t = u)
            }
          }
          return t
        }
        i = i || 0;
        for (var c = e.length; c > 0 && e[c - 1][2] > i; c--) e[c] = e[c - 1];
        e[c] = [o, s, i]
      }
    }(),
    function () {
      n.n = function (e) {
        var t = e && e.__esModule ? function () {
          return e["default"]
        } : function () {
          return e
        };
        return n.d(t, {
          a: t
        }), t
      }
    }(),
    function () {
      n.d = function (e, t) {
        for (var o in t) n.o(t, o) && !n.o(e, o) && Object.defineProperty(e, o, {
          enumerable: !0,
          get: t[o]
        })
      }
    }(),
    function () {
      n.g = function () {
        if ("object" === typeof globalThis) return globalThis;
        try {
          return this || new Function("return this")()
        } catch (e) {
          if ("object" === typeof window) return window
        }
      }()
    }(),
    function () {
      n.o = function (e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
      }
    }(),
    function () {
      var e = {
        143: 0
      };
      n.O.j = function (t) {
        return 0 === e[t]
      };
      var t = function (t, o) {
          var s, i, a = o[0],
            l = o[1],
            r = o[2],
            u = 0;
          if (a.some((function (t) {
              return 0 !== e[t]
            }))) {
            for (s in l) n.o(l, s) && (n.m[s] = l[s]);
            if (r) var c = r(n)
          }
          for (t && t(o); u < a.length; u++) i = a[u], n.o(e, i) && e[i] && e[i][0](), e[i] = 0;
          return n.O(c)
        },
        o = self["webpackChunkabout_ocr"] = self["webpackChunkabout_ocr"] || [];
      o.forEach(t.bind(null, 0)), o.push = t.bind(null, o.push.bind(o))
    }();
  var o = n.O(void 0, [998], (function () {
    return n(8056)
  }));
  o = n.O(o)
})();
//# sourceMappingURL=app.a267cfd1.js.map