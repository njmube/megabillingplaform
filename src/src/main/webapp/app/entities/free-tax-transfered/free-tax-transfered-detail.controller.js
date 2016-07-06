(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_tax_transferedDetailController', Free_tax_transferedDetailController);

    Free_tax_transferedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_tax_transfered', 'Free_concept', 'Tax_types'];

    function Free_tax_transferedDetailController($scope, $rootScope, $stateParams, entity, Free_tax_transfered, Free_concept, Tax_types) {
        var vm = this;
        vm.free_tax_transfered = entity;
        vm.load = function (id) {
            Free_tax_transfered.get({id: id}, function(result) {
                vm.free_tax_transfered = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_tax_transferedUpdate', function(event, result) {
            vm.free_tax_transfered = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
