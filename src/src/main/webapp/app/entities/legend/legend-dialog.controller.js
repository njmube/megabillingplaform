(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('LegendDialogController', LegendDialogController);

    LegendDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Legend'];

    function LegendDialogController ($scope, $stateParams, $uibModalInstance, entity, Legend) {
        var vm = this;
        vm.legend = entity;

        vm.load = function(id) {
            Legend.get({id : id}, function(result) {
                vm.legend = result;
            });
        };

        vm.save = function () {
            $uibModalInstance.close(vm.legend);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
