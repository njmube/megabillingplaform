(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptDetailController', Tax_conceptDetailController);

    Tax_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_concept', 'Tax_types', 'Taxpayer_concept'];

    function Tax_conceptDetailController($scope, $rootScope, $stateParams, entity, Tax_concept, Tax_types, Taxpayer_concept) {
        var vm = this;
        vm.tax_concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_conceptUpdate', function(event, result) {
            vm.tax_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
