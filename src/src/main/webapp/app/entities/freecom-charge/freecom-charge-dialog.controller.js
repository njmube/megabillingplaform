(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_chargeDialogController', Freecom_chargeDialogController);

    Freecom_chargeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_charge'];

    function Freecom_chargeDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_charge) {
        var vm = this;
        vm.freecom_charge = entity;

        vm.load = function(id) {
            Freecom_charge.get({id : id}, function(result) {
                vm.freecom_charge = result;
            });
        };

        vm.save = function () {
            $uibModalInstance.close(vm.freecom_charge);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
