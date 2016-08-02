(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_retentions_transferedDialogController', Freecom_retentions_transferedDialogController);

    Freecom_retentions_transferedDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_retentions_transfered'];

    function Freecom_retentions_transferedDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_retentions_transfered) {
        var vm = this;
        vm.freecom_retentions_transfered = entity;
        vm.load = function(id) {
            Freecom_retentions_transfered.get({id : id}, function(result) {
                vm.freecom_retentions_transfered = result;
            });
        };

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_retentions_transfered);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
