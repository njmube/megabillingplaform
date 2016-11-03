(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_legendsDeleteController',Com_legendsDeleteController);

    Com_legendsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_legends'];

    function Com_legendsDeleteController($uibModalInstance, entity, Com_legends) {
        var vm = this;
        vm.com_legends = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_legends.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
