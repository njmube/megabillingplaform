(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_accreditation_iepsDeleteController',Com_accreditation_iepsDeleteController);

    Com_accreditation_iepsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_accreditation_ieps'];

    function Com_accreditation_iepsDeleteController($uibModalInstance, entity, Com_accreditation_ieps) {
        var vm = this;
        vm.com_accreditation_ieps = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_accreditation_ieps.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
