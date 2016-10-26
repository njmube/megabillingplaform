(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('MessTransactionsController', MessTransactionsController);

    MessTransactionsController.$inject = ['$uibModalInstance'];

    function MessTransactionsController ($uibModalInstance) {
        var vm = this;

        vm.close = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

