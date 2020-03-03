module.exports = function(config) {
    config.set({
        plugins: ['karma-chrome-launcher', 'karma-scalajs-scalatest'],
        reporters: ['progress'],
        frameworks: ['scalajs-scalatest'],
        files: [
            'app/js/target/scala-2.12/cryptotpyrc-test-jsdeps.js',
            'app/js/target/scala-2.12/cryptotpyrc-test-fastopt.js'
        ],

        browsers: process.env.TRAVIS ? ['Firefox'] : ['Chrome'],

        autoWatch: true,

        client: {
            tests: [
                "pl.setblack.lsa.cryptotpyrc.rsa.js.RSACryptoAlgTest"
            ]
        }
    });
};