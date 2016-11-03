(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_info_customs_destructionDeleteController',Com_info_customs_destructionDeleteController);

    Com_info_customs_destructionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_info_customs_destruction'];

    function Com_info_customs_destructionDeleteController($uibModalInstance, entity, Com_info_customs_destruction) {
        var vm = this;
        vm.com_info_customs_destruction = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_info_customs_destruction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
