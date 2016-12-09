(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_notary_dataDeleteController',Com_notary_dataDeleteController);

    Com_notary_dataDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_notary_data'];

    function Com_notary_dataDeleteController($uibModalInstance, entity, Com_notary_data) {
        var vm = this;
        vm.com_notary_data = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_notary_data.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
