(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_incotermDeleteController',Com_incotermDeleteController);

    Com_incotermDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_incoterm'];

    function Com_incotermDeleteController($uibModalInstance, entity, Com_incoterm) {
        var vm = this;
        vm.com_incoterm = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_incoterm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
