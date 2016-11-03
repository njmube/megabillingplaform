(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_taxregistrationDeleteController',Com_taxregistrationDeleteController);

    Com_taxregistrationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_taxregistration'];

    function Com_taxregistrationDeleteController($uibModalInstance, entity, Com_taxregistration) {
        var vm = this;
        vm.com_taxregistration = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_taxregistration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
