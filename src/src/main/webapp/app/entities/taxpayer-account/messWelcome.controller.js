(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('MessWelcomeController', MessWelcomeController);

    MessWelcomeController.$inject = ['$uibModalInstance'];

    function MessWelcomeController ($uibModalInstance) {
        var vm = this;

        vm.close = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();


