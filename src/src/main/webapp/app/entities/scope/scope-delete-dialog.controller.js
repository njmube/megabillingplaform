(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ScopeDeleteController',ScopeDeleteController);

    ScopeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Scope'];

    function ScopeDeleteController($uibModalInstance, entity, Scope) {
        var vm = this;
        vm.scope = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Scope.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
