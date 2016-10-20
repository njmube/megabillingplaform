(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_retentionsDetailController', Tax_retentionsDetailController);

    Tax_retentionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_retentions', 'Tax_types', 'Concept'];

    function Tax_retentionsDetailController($scope, $rootScope, $stateParams, entity, Tax_retentions, Tax_types, Concept) {
        var vm = this;
        vm.tax_retentions = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_retentionsUpdate', function(event, result) {
            vm.tax_retentions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
