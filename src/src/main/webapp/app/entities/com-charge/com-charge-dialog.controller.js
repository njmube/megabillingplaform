(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_chargeDialogController', Com_chargeDialogController);

    Com_chargeDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_chargeDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.com_charge = entity;

        vm.save = function () {
            $uibModalInstance.close(vm.com_charge);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
