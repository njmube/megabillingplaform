(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_typesDetailController', Tax_typesDetailController);

    Tax_typesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_types'];

    function Tax_typesDetailController($scope, $rootScope, $stateParams, entity, Tax_types) {
        var vm = this;

        vm.tax_types = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_typesUpdate', function(event, result) {
            vm.tax_types = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
