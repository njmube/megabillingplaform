(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_addresseeDetailController', Com_addresseeDetailController);

    Com_addresseeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_addressee', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Com_addresseeDetailController($scope, $rootScope, $stateParams, entity, Com_addressee, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;
        vm.com_addressee = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_addresseeUpdate', function(event, result) {
            vm.com_addressee = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
