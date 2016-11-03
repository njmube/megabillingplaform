(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_educational_institutionsDetailController', Com_educational_institutionsDetailController);

    Com_educational_institutionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_educational_institutions', 'Cfdi', 'C_school_level'];

    function Com_educational_institutionsDetailController($scope, $rootScope, $stateParams, entity, Com_educational_institutions, Cfdi, C_school_level) {
        var vm = this;
        vm.com_educational_institutions = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_educational_institutionsUpdate', function(event, result) {
            vm.com_educational_institutions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
