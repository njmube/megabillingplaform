(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_beneficiaryDeleteController',Com_beneficiaryDeleteController);

    Com_beneficiaryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_beneficiary'];

    function Com_beneficiaryDeleteController($uibModalInstance, entity, Com_beneficiary) {
        var vm = this;
        vm.com_beneficiary = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_beneficiary.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
